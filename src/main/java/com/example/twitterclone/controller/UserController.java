package com.example.twitterclone.controller;

import com.example.twitterclone.dto.UserDto;
import com.example.twitterclone.entity.User;
import com.example.twitterclone.exception.BadRequestException;
import com.example.twitterclone.exception.ResourceNotFoundException;
import com.example.twitterclone.repository.FollowRepository;
import com.example.twitterclone.service.UserService;
import com.example.twitterclone.util.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for user operations.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management APIs")
public class UserController {
    
    private final UserService userService;
    private final FollowRepository followRepository;
    
    /**
     * Constructor with dependencies.
     *
     * @param userService the user service
     * @param followRepository the follow repository
     */
    public UserController(UserService userService, FollowRepository followRepository) {
        this.userService = userService;
        this.followRepository = followRepository;
    }
    
    /**
     * Get a user by username.
     *
     * @param username the username
     * @return the user DTO
     */
    @Operation(summary = "Get a user by username", description = "Returns a user based on the provided username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found", 
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(
            @Parameter(description = "Username of the user to retrieve") @PathVariable String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        // Get follower counts
        long followersCount = followRepository.countByFollowingId(user.getId());
        long followingCount = followRepository.countByFollowerId(user.getId());
        
        return ResponseEntity.ok(UserMapper.toDtoWithCounts(user, followersCount, followingCount));
    }
    
    /**
     * Create a new user.
     *
     * @param userDto the user details
     * @return the created user DTO
     */
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully", 
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or username/email already taken")
    })
    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @Parameter(description = "User details") @Valid @RequestBody UserDto userDto) {
        // Check if username is available
        if (!userService.isUsernameAvailable(userDto.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }
        
        // Check if email is available
        if (!userService.isEmailAvailable(userDto.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }
        
        // Convert DTO to entity
        User user = UserMapper.toEntity(userDto);
        
        // Create user
        User createdUser = userService.createUser(user);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(createdUser));
    }
    
    /**
     * Update a user.
     *
     * @param username the username
     * @param userDto the updated user details
     * @return the updated user DTO
     */
    @Operation(summary = "Update a user", description = "Updates an existing user with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully", 
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{username}")
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "Username of the user to update") @PathVariable String username,
            @Parameter(description = "Updated user details") @Valid @RequestBody UserDto userDto) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        // Update user
        User updatedUser = userService.updateUser(user.getId(), UserMapper.updateEntity(user, userDto));
        
        return ResponseEntity.ok(UserMapper.toDto(updatedUser));
    }
    
    /**
     * Delete a user.
     *
     * @param username the username
     * @return no content response
     */
    @Operation(summary = "Delete a user", description = "Deletes a user with the specified username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "Username of the user to delete") @PathVariable String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        userService.deleteUser(user.getId());
        
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Search for users.
     *
     * @param query the search query
     * @return list of matching user DTOs
     */
    @Operation(summary = "Search for users", description = "Returns users matching the search query")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search results retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsers(
            @Parameter(description = "Search query") @RequestParam String query) {
        List<User> users = userService.searchUsers(query);
        
        List<UserDto> userDtos = users.stream()
                .map(user -> {
                    long followersCount = followRepository.countByFollowingId(user.getId());
                    long followingCount = followRepository.countByFollowerId(user.getId());
                    return UserMapper.toDtoWithCounts(user, followersCount, followingCount);
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(userDtos);
    }
    
    /**
     * Check if a username is available.
     *
     * @param username the username to check
     * @return availability status
     */
    @Operation(summary = "Check username availability", description = "Checks if a username is available for registration")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Availability status retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = Boolean.class)))
    })
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> isUsernameAvailable(
            @Parameter(description = "Username to check") @RequestParam String username) {
        return ResponseEntity.ok(userService.isUsernameAvailable(username));
    }
    
    /**
     * Check if an email is available.
     *
     * @param email the email to check
     * @return availability status
     */
    @Operation(summary = "Check email availability", description = "Checks if an email is available for registration")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Availability status retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = Boolean.class)))
    })
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> isEmailAvailable(
            @Parameter(description = "Email to check") @RequestParam String email) {
        return ResponseEntity.ok(userService.isEmailAvailable(email));
    }
} 