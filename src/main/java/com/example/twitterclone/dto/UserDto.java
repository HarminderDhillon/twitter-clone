package com.example.twitterclone.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for User entity.
 */
@Schema(description = "User data transfer object")
public class UserDto {
    
    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Username of the user", example = "johndoe", minLength = 3, maxLength = 50, required = true)
    private String username;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    private String email;
    
    @Schema(description = "Display name of the user", example = "John Doe")
    private String displayName;
    
    @Size(max = 160, message = "Bio cannot exceed 160 characters")
    @Schema(description = "Biography of the user", example = "Software developer and tech enthusiast", maxLength = 160)
    private String bio;
    
    @Schema(description = "Location of the user", example = "San Francisco, CA")
    private String location;
    
    @Schema(description = "Website of the user", example = "https://johndoe.example.com")
    private String website;
    
    @Schema(description = "Profile image URL of the user", example = "/images/profile/johndoe.jpg")
    private String profileImage;
    
    @Schema(description = "Header image URL of the user", example = "/images/header/johndoe.jpg")
    private String headerImage;
    
    @Schema(description = "Flag indicating if the user is verified", example = "false")
    private boolean verified;
    
    @Schema(description = "Timestamp when the user was created", example = "2023-03-15T14:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Number of followers the user has", example = "1024")
    private int followersCount;
    
    @Schema(description = "Number of users the user is following", example = "512")
    private int followingCount;
    
    // For registration/update
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Schema(description = "Password for user registration or update", example = "SecureP@ssw0rd", minLength = 8, maxLength = 100, accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;
    
    // Default constructor
    public UserDto() {
    }
    
    // Getters and Setters
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public String getProfileImage() {
        return profileImage;
    }
    
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    
    public String getHeaderImage() {
        return headerImage;
    }
    
    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }
    
    public boolean isVerified() {
        return verified;
    }
    
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public int getFollowersCount() {
        return followersCount;
    }
    
    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }
    
    public int getFollowingCount() {
        return followingCount;
    }
    
    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
} 