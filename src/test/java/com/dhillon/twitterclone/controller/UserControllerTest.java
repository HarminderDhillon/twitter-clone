package com.dhillon.twitterclone.controller;

import com.dhillon.twitterclone.dto.UserDto;
import com.dhillon.twitterclone.entity.User;
import com.dhillon.twitterclone.repository.FollowRepository;
import com.dhillon.twitterclone.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller tests for UserController.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private FollowRepository followRepository;
    
    private User testUser;
    private UUID testUserId;
    
    @BeforeEach
    public void setup() {
        testUserId = UUID.randomUUID();
        testUser = new User();
        testUser.setId(testUserId);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPasswordHash("hashedpassword");
        testUser.setDisplayName("Test User");
    }
    
    @Test
    public void getUserByUsername_WhenUserExists_ReturnsUser() throws Exception {
        // Arrange
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(followRepository.countByFollowingId(testUserId)).thenReturn(10L);
        when(followRepository.countByFollowerId(testUserId)).thenReturn(20L);
        
        // Act & Assert
        mockMvc.perform(get("/api/users/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testUserId.toString())))
                .andExpect(jsonPath("$.username", is("testuser")))
                .andExpect(jsonPath("$.displayName", is("Test User")))
                .andExpect(jsonPath("$.followersCount", is(10)))
                .andExpect(jsonPath("$.followingCount", is(20)));
        
        verify(userService).findByUsername("testuser");
        verify(followRepository).countByFollowingId(testUserId);
        verify(followRepository).countByFollowerId(testUserId);
    }
    
    @Test
    public void getUserByUsername_WhenUserDoesNotExist_ReturnsNotFound() throws Exception {
        // Arrange
        when(userService.findByUsername("nonexistent")).thenReturn(Optional.empty());
        
        // Act & Assert
        mockMvc.perform(get("/api/users/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")));
        
        verify(userService).findByUsername("nonexistent");
    }
    
    @Test
    public void createUser_WithValidData_ReturnsCreated() throws Exception {
        // Arrange
        UserDto userDto = new UserDto(
            null,
            "newuser",
            "new@example.com",
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            0,
            "password123"
        );
        
        User newUser = new User();
        newUser.setId(UUID.randomUUID());
        newUser.setUsername("newuser");
        newUser.setEmail("new@example.com");
        
        when(userService.isUsernameAvailable("newuser")).thenReturn(true);
        when(userService.isEmailAvailable("new@example.com")).thenReturn(true);
        when(userService.createUser(any(User.class))).thenReturn(newUser);
        
        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("newuser")))
                .andExpect(jsonPath("$.email", is("new@example.com")));
        
        verify(userService).isUsernameAvailable("newuser");
        verify(userService).isEmailAvailable("new@example.com");
        verify(userService).createUser(any(User.class));
    }
    
    @Test
    public void createUser_WithExistingUsername_ReturnsBadRequest() throws Exception {
        // Arrange
        UserDto userDto = new UserDto(
            null,
            "existing",
            "new@example.com",
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            null,
            0,
            0,
            "password123"
        );
        
        when(userService.isUsernameAvailable("existing")).thenReturn(false);
        
        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.message", containsString("Username is already taken")));
        
        verify(userService).isUsernameAvailable("existing");
        verify(userService, never()).createUser(any(User.class));
    }
    
    @Test
    public void searchUsers_ReturnsMatchingUsers() throws Exception {
        // Arrange
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setUsername("test1");
        
        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setUsername("test2");
        
        when(userService.searchUsers("test")).thenReturn(Arrays.asList(user1, user2));
        when(followRepository.countByFollowingId(any(UUID.class))).thenReturn(0L);
        when(followRepository.countByFollowerId(any(UUID.class))).thenReturn(0L);
        
        // Act & Assert
        mockMvc.perform(get("/api/users/search").param("query", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("test1")))
                .andExpect(jsonPath("$[1].username", is("test2")));
        
        verify(userService).searchUsers("test");
    }
    
    @Test
    public void isUsernameAvailable_WhenAvailable_ReturnsTrue() throws Exception {
        // Arrange
        when(userService.isUsernameAvailable("available")).thenReturn(true);
        
        // Act & Assert
        mockMvc.perform(get("/api/users/check-username").param("username", "available"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        
        verify(userService).isUsernameAvailable("available");
    }
    
    @Test
    public void isUsernameAvailable_WhenNotAvailable_ReturnsFalse() throws Exception {
        // Arrange
        when(userService.isUsernameAvailable("taken")).thenReturn(false);
        
        // Act & Assert
        mockMvc.perform(get("/api/users/check-username").param("username", "taken"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
        
        verify(userService).isUsernameAvailable("taken");
    }
} 