package com.dhillon.twitterclone.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for Post entity.
 */
@Schema(description = "Post data transfer object")
public record PostDto(
    @Schema(description = "Unique identifier of the post", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID id,
    
    @Schema(description = "Unique identifier of the user who created the post", example = "123e4567-e89b-12d3-a456-426614174001")
    UUID userId,
    
    @Schema(description = "Username of the user who created the post", example = "johndoe")
    String username,
    
    @Schema(description = "Display name of the user who created the post", example = "John Doe")
    String userDisplayName,
    
    @Schema(description = "Profile image URL of the user who created the post", example = "/images/profile/johndoe.jpg")
    String userProfileImage,
    
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 280, message = "Content cannot exceed 280 characters")
    @Schema(description = "Content of the post", example = "This is a sample post #twitter #clone", maxLength = 280, required = true)
    String content,
    
    @Schema(description = "List of media URLs attached to the post", example = "['/images/media/post1.jpg', '/images/media/post2.jpg']")
    List<String> media,
    
    @Schema(description = "Flag indicating if the post is a reply to another post", example = "false")
    boolean isReply,
    
    @Schema(description = "Unique identifier of the parent post if this is a reply", example = "123e4567-e89b-12d3-a456-426614174002")
    UUID parentId,
    
    @Schema(description = "Flag indicating if the post is a repost of another post", example = "false")
    boolean isRepost,
    
    @Schema(description = "Unique identifier of the original post if this is a repost", example = "123e4567-e89b-12d3-a456-426614174003")
    UUID originalPostId,
    
    @Schema(description = "List of hashtags in the post", example = "['twitter', 'clone']")
    List<String> hashtags,
    
    @Schema(description = "Number of likes the post has received", example = "42")
    int likeCount,
    
    @Schema(description = "Number of replies the post has received", example = "7")
    int replyCount,
    
    @Schema(description = "Number of reposts the post has received", example = "3")
    int repostCount,
    
    @Schema(description = "Timestamp when the post was created", example = "2023-03-15T14:30:00")
    LocalDateTime createdAt,
    
    @Schema(description = "Timestamp when the post was last updated", example = "2023-03-15T15:45:00")
    LocalDateTime updatedAt
) {
    /**
     * Default constructor for deserialization.
     */
    public PostDto() {
        this(null, null, null, null, null, null, new ArrayList<>(), false, null, false, null, 
             new ArrayList<>(), 0, 0, 0, null, null);
    }
    
    /**
     * Constructor with required fields.
     *
     * @param content the content of the post
     */
    public PostDto(String content) {
        this(null, null, null, null, null, content, new ArrayList<>(), false, null, false, null, 
             new ArrayList<>(), 0, 0, 0, null, null);
    }
    
    /**
     * Compact constructor to ensure lists are never null.
     */
    public PostDto {
        media = media == null ? new ArrayList<>() : media;
        hashtags = hashtags == null ? new ArrayList<>() : hashtags;
    }
} 