package com.example.twitterclone.controller;

import com.example.twitterclone.dto.PostDto;
import com.example.twitterclone.entity.Post;
import com.example.twitterclone.entity.User;
import com.example.twitterclone.exception.ResourceNotFoundException;
import com.example.twitterclone.service.PostService;
import com.example.twitterclone.service.UserService;
import com.example.twitterclone.util.PostMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for post operations.
 */
@RestController
@RequestMapping("/posts")
@Tag(name = "Posts", description = "Post management APIs")
public class PostController {
    
    private final PostService postService;
    private final UserService userService;
    
    /**
     * Constructor with dependencies.
     *
     * @param postService the post service
     * @param userService the user service
     */
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }
    
    /**
     * Get a post by ID.
     *
     * @param id the post ID
     * @return the post DTO
     */
    @Operation(summary = "Get a post by its ID", description = "Returns a post based on the provided ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post found", 
                    content = @Content(schema = @Schema(implementation = PostDto.class))),
        @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(
            @Parameter(description = "ID of the post to retrieve") @PathVariable UUID id) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        
        return ResponseEntity.ok(PostMapper.toDto(post));
    }
    
    /**
     * Create a new post.
     *
     * @param postDto the post details
     * @param username the username of the post creator
     * @return the created post DTO
     */
    @Operation(summary = "Create a new post", description = "Creates a new post for the specified user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Post created successfully", 
                    content = @Content(schema = @Schema(implementation = PostDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/user/{username}")
    public ResponseEntity<PostDto> createPost(
            @Parameter(description = "Post details") @Valid @RequestBody PostDto postDto,
            @Parameter(description = "Username of the post creator") @PathVariable String username) {
        
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        // Convert DTO to entity
        Post post = PostMapper.toEntity(postDto);
        post.setUser(user);
        
        // Create post
        Post savedPost = postService.createPost(post);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(PostMapper.toDto(savedPost));
    }
    
    /**
     * Update a post.
     *
     * @param id the post ID
     * @param postDto the updated post details
     * @return the updated post DTO
     */
    @Operation(summary = "Update a post", description = "Updates an existing post with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post updated successfully", 
                    content = @Content(schema = @Schema(implementation = PostDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @Parameter(description = "ID of the post to update") @PathVariable UUID id,
            @Parameter(description = "Updated post details") @Valid @RequestBody PostDto postDto) {
        
        // Convert DTO to entity
        Post post = PostMapper.toEntity(postDto);
        
        // Update post
        Post updatedPost = postService.updatePost(id, post);
        
        return ResponseEntity.ok(PostMapper.toDto(updatedPost));
    }
    
    /**
     * Delete a post.
     *
     * @param id the post ID
     * @return no content response
     */
    @Operation(summary = "Delete a post", description = "Deletes a post with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "ID of the post to delete") @PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Get user timeline (posts by a specific user).
     *
     * @param username the username
     * @param page the page number (0-based)
     * @param size the page size
     * @return page of post DTOs
     */
    @Operation(summary = "Get user timeline", description = "Returns posts created by a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Timeline retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{username}")
    public ResponseEntity<Page<PostDto>> getUserTimeline(
            @Parameter(description = "Username of the user") @PathVariable String username,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.getUserTimeline(user.getId(), pageable);
        
        Page<PostDto> postDtos = posts.map(PostMapper::toDto);
        
        return ResponseEntity.ok(postDtos);
    }
    
    /**
     * Get home timeline (posts from followed users).
     *
     * @param username the username
     * @param page the page number (0-based)
     * @param size the page size
     * @return page of post DTOs
     */
    @Operation(summary = "Get home timeline", description = "Returns posts from users followed by the specified user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Home timeline retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/home/{username}")
    public ResponseEntity<Page<PostDto>> getHomeTimeline(
            @Parameter(description = "Username of the user") @PathVariable String username,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.getHomeTimeline(user.getId(), pageable);
        
        Page<PostDto> postDtos = posts.map(PostMapper::toDto);
        
        return ResponseEntity.ok(postDtos);
    }
    
    /**
     * Search for posts.
     *
     * @param query the search query
     * @param page the page number (0-based)
     * @param size the page size
     * @return page of post DTOs
     */
    @Operation(summary = "Search for posts", description = "Returns posts matching the search query")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search results retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<Page<PostDto>> searchPosts(
            @Parameter(description = "Search query") @RequestParam String query,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.searchPosts(query, pageable);
        
        Page<PostDto> postDtos = posts.map(PostMapper::toDto);
        
        return ResponseEntity.ok(postDtos);
    }
    
    /**
     * Get trending posts.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @return page of post DTOs
     */
    @Operation(summary = "Get trending posts", description = "Returns trending posts based on engagement metrics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trending posts retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/trending")
    public ResponseEntity<Page<PostDto>> getTrendingPosts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.getTrendingPosts(pageable);
        
        Page<PostDto> postDtos = posts.map(PostMapper::toDto);
        
        return ResponseEntity.ok(postDtos);
    }
    
    /**
     * Get posts by hashtag.
     *
     * @param hashtag the hashtag name
     * @param page the page number (0-based)
     * @param size the page size
     * @return page of post DTOs
     */
    @Operation(summary = "Get posts by hashtag", description = "Returns posts containing the specified hashtag")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Posts retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/hashtag/{hashtag}")
    public ResponseEntity<Page<PostDto>> getPostsByHashtag(
            @Parameter(description = "Hashtag name (without #)") @PathVariable String hashtag,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.getPostsByHashtag(hashtag, pageable);
        
        Page<PostDto> postDtos = posts.map(PostMapper::toDto);
        
        return ResponseEntity.ok(postDtos);
    }
    
    /**
     * Get replies to a post.
     *
     * @param id the parent post ID
     * @param page the page number (0-based)
     * @param size the page size
     * @return page of reply post DTOs
     */
    @Operation(summary = "Get replies to a post", description = "Returns replies to the specified post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Replies retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "404", description = "Parent post not found")
    })
    @GetMapping("/{id}/replies")
    public ResponseEntity<Page<PostDto>> getReplies(
            @Parameter(description = "ID of the parent post") @PathVariable UUID id,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        // Check if parent post exists
        if (!postService.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Post", "id", id);
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> replies = postService.getReplies(id, pageable);
        
        Page<PostDto> replyDtos = replies.map(PostMapper::toDto);
        
        return ResponseEntity.ok(replyDtos);
    }
    
    /**
     * Create a reply to a post.
     *
     * @param parentId the parent post ID
     * @param postDto the reply post details
     * @param username the username of the reply creator
     * @return the created reply post DTO
     */
    @Operation(summary = "Create a reply to a post", description = "Creates a reply to the specified post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reply created successfully", 
                    content = @Content(schema = @Schema(implementation = PostDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Parent post or user not found")
    })
    @PostMapping("/{parentId}/reply/{username}")
    public ResponseEntity<PostDto> createReply(
            @Parameter(description = "ID of the parent post") @PathVariable UUID parentId,
            @Parameter(description = "Reply details") @Valid @RequestBody PostDto postDto,
            @Parameter(description = "Username of the reply creator") @PathVariable String username) {
        
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        // Convert DTO to entity
        Post reply = PostMapper.toEntity(postDto);
        reply.setUser(user);
        
        // Create reply
        Post savedReply = postService.createReply(parentId, reply);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(PostMapper.toDto(savedReply));
    }
    
    /**
     * Create a repost.
     *
     * @param originalPostId the original post ID
     * @param postDto the repost details
     * @param username the username of the repost creator
     * @return the created repost DTO
     */
    @Operation(summary = "Create a repost", description = "Creates a repost of the specified post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Repost created successfully", 
                    content = @Content(schema = @Schema(implementation = PostDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Original post or user not found")
    })
    @PostMapping("/{originalPostId}/repost/{username}")
    public ResponseEntity<PostDto> createRepost(
            @Parameter(description = "ID of the original post") @PathVariable UUID originalPostId,
            @Parameter(description = "Repost details") @Valid @RequestBody PostDto postDto,
            @Parameter(description = "Username of the repost creator") @PathVariable String username) {
        
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        // Convert DTO to entity
        Post repost = PostMapper.toEntity(postDto);
        repost.setUser(user);
        
        // Create repost
        Post savedRepost = postService.createRepost(originalPostId, repost);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(PostMapper.toDto(savedRepost));
    }
} 