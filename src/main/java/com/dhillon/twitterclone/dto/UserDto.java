package com.dhillon.twitterclone.dto;

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
public record UserDto(
    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID id,
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Username of the user", example = "johndoe", minLength = 3, maxLength = 50, required = true)
    String username,
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    String email,
    
    @Schema(description = "Display name of the user", example = "John Doe")
    String displayName,
    
    @Size(max = 160, message = "Bio cannot exceed 160 characters")
    @Schema(description = "Biography of the user", example = "Software developer and tech enthusiast", maxLength = 160)
    String bio,
    
    @Schema(description = "Location of the user", example = "San Francisco, CA")
    String location,
    
    @Schema(description = "Website of the user", example = "https://johndoe.example.com")
    String website,
    
    @Schema(description = "Profile image URL of the user", example = "/images/profile/johndoe.jpg")
    String profileImage,
    
    @Schema(description = "Header image URL of the user", example = "/images/header/johndoe.jpg")
    String headerImage,
    
    @Schema(description = "Flag indicating if the user is verified", example = "false")
    boolean verified,
    
    @Schema(description = "Timestamp when the user was created", example = "2023-03-15T14:30:00")
    LocalDateTime createdAt,
    
    @Schema(description = "Number of followers the user has", example = "1024")
    int followersCount,
    
    @Schema(description = "Number of users the user is following", example = "512")
    int followingCount,
    
    // For registration/update
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Schema(description = "Password for user registration or update", example = "SecureP@ssw0rd", minLength = 8, maxLength = 100, accessMode = Schema.AccessMode.WRITE_ONLY)
    String password
) {
    /**
     * Default constructor for deserialization.
     */
    public UserDto() {
        this(null, null, null, null, null, null, null, null, null, false, null, 0, 0, null);
    }
    
    /**
     * Compact constructor to validate and normalize data.
     */
    public UserDto {
        // Normalize username to lowercase if not null
        if (username != null) {
            username = username.toLowerCase();
        }
        
        // Normalize email to lowercase if not null
        if (email != null) {
            email = email.toLowerCase();
        }
    }
} 