package com.example.twitterclone.dto;

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
public class PostDto {
    
    @Schema(description = "Unique identifier of the post", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    
    @Schema(description = "Unique identifier of the user who created the post", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID userId;
    
    @Schema(description = "Username of the user who created the post", example = "johndoe")
    private String username;
    
    @Schema(description = "Display name of the user who created the post", example = "John Doe")
    private String userDisplayName;
    
    @Schema(description = "Profile image URL of the user who created the post", example = "/images/profile/johndoe.jpg")
    private String userProfileImage;
    
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 280, message = "Content cannot exceed 280 characters")
    @Schema(description = "Content of the post", example = "This is a sample post #twitter #clone", maxLength = 280, required = true)
    private String content;
    
    @Schema(description = "List of media URLs attached to the post", example = "['/images/media/post1.jpg', '/images/media/post2.jpg']")
    private List<String> media = new ArrayList<>();
    
    @Schema(description = "Flag indicating if the post is a reply to another post", example = "false")
    private boolean isReply;
    
    @Schema(description = "Unique identifier of the parent post if this is a reply", example = "123e4567-e89b-12d3-a456-426614174002")
    private UUID parentId;
    
    @Schema(description = "Flag indicating if the post is a repost of another post", example = "false")
    private boolean isRepost;
    
    @Schema(description = "Unique identifier of the original post if this is a repost", example = "123e4567-e89b-12d3-a456-426614174003")
    private UUID originalPostId;
    
    @Schema(description = "List of hashtags in the post", example = "['twitter', 'clone']")
    private List<String> hashtags = new ArrayList<>();
    
    @Schema(description = "Number of likes the post has received", example = "42")
    private int likeCount;
    
    @Schema(description = "Number of replies the post has received", example = "7")
    private int replyCount;
    
    @Schema(description = "Number of reposts the post has received", example = "3")
    private int repostCount;
    
    @Schema(description = "Timestamp when the post was created", example = "2023-03-15T14:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Timestamp when the post was last updated", example = "2023-03-15T15:45:00")
    private LocalDateTime updatedAt;
    
    /**
     * Default constructor.
     */
    public PostDto() {
    }
    
    /**
     * Constructor with required fields.
     *
     * @param content the content of the post
     */
    public PostDto(String content) {
        this.content = content;
    }
    
    /**
     * Full constructor.
     *
     * @param id the ID
     * @param userId the user ID
     * @param username the username
     * @param userDisplayName the user display name
     * @param userProfileImage the user profile image
     * @param content the content
     * @param media the media URLs
     * @param isReply whether it's a reply
     * @param parentId the parent post ID
     * @param isRepost whether it's a repost
     * @param originalPostId the original post ID
     * @param hashtags the hashtags
     * @param likeCount the like count
     * @param replyCount the reply count
     * @param repostCount the repost count
     * @param createdAt the creation timestamp
     * @param updatedAt the update timestamp
     */
    public PostDto(UUID id, UUID userId, String username, String userDisplayName, String userProfileImage,
                  String content, List<String> media, boolean isReply, UUID parentId, boolean isRepost,
                  UUID originalPostId, List<String> hashtags, int likeCount, int replyCount, int repostCount,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.userDisplayName = userDisplayName;
        this.userProfileImage = userProfileImage;
        this.content = content;
        this.media = media;
        this.isReply = isReply;
        this.parentId = parentId;
        this.isRepost = isRepost;
        this.originalPostId = originalPostId;
        this.hashtags = hashtags;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.repostCount = repostCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public UUID getUserId() {
        return userId;
    }
    
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUserDisplayName() {
        return userDisplayName;
    }
    
    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }
    
    public String getUserProfileImage() {
        return userProfileImage;
    }
    
    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public List<String> getMedia() {
        return media;
    }
    
    public void setMedia(List<String> media) {
        this.media = media;
    }
    
    public boolean isReply() {
        return isReply;
    }
    
    public void setReply(boolean reply) {
        isReply = reply;
    }
    
    public UUID getParentId() {
        return parentId;
    }
    
    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }
    
    public boolean isRepost() {
        return isRepost;
    }
    
    public void setRepost(boolean repost) {
        isRepost = repost;
    }
    
    public UUID getOriginalPostId() {
        return originalPostId;
    }
    
    public void setOriginalPostId(UUID originalPostId) {
        this.originalPostId = originalPostId;
    }
    
    public List<String> getHashtags() {
        return hashtags;
    }
    
    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }
    
    public int getLikeCount() {
        return likeCount;
    }
    
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
    
    public int getReplyCount() {
        return replyCount;
    }
    
    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }
    
    public int getRepostCount() {
        return repostCount;
    }
    
    public void setRepostCount(int repostCount) {
        this.repostCount = repostCount;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 