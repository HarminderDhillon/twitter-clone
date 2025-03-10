package com.dhillon.twitterclone.util;

import com.dhillon.twitterclone.dto.UserDto;
import com.dhillon.twitterclone.entity.User;
import com.dhillon.twitterclone.repository.FollowRepository;

/**
 * Mapper utility class for User and UserDto conversion.
 */
public class UserMapper {
    
    private UserMapper() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Convert User entity to UserDto.
     *
     * @param user the user entity
     * @return the user DTO
     */
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setDisplayName(user.getDisplayName());
        dto.setBio(user.getBio());
        dto.setLocation(user.getLocation());
        dto.setWebsite(user.getWebsite());
        dto.setProfileImage(user.getProfileImage());
        dto.setHeaderImage(user.getHeaderImage());
        dto.setVerified(user.isVerified());
        dto.setCreatedAt(user.getCreatedAt());
        
        return dto;
    }
    
    /**
     * Convert User entity to UserDto with follower counts.
     *
     * @param user the user entity
     * @param followersCount the followers count
     * @param followingCount the following count
     * @return the user DTO with follower counts
     */
    public static UserDto toDtoWithCounts(User user, long followersCount, long followingCount) {
        UserDto dto = toDto(user);
        if (dto != null) {
            dto.setFollowersCount((int) followersCount);
            dto.setFollowingCount((int) followingCount);
        }
        return dto;
    }
    
    /**
     * Convert UserDto to User entity.
     *
     * @param dto the user DTO
     * @return the user entity
     */
    public static User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User();
        
        // ID is typically not set when creating a new user
        if (dto.getId() != null) {
            user.setId(dto.getId());
        }
        
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        
        // For password, we only set it if it's a new user or changing password
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPasswordHash(dto.getPassword()); // This will be encoded by the service
        }
        
        user.setDisplayName(dto.getDisplayName() != null ? dto.getDisplayName() : dto.getUsername());
        user.setBio(dto.getBio());
        user.setLocation(dto.getLocation());
        user.setWebsite(dto.getWebsite());
        user.setProfileImage(dto.getProfileImage());
        user.setHeaderImage(dto.getHeaderImage());
        
        return user;
    }
    
    /**
     * Update an existing User entity with data from a UserDto.
     *
     * @param user the existing user entity
     * @param dto the user DTO with updates
     * @return the updated user entity
     */
    public static User updateEntity(User user, UserDto dto) {
        if (user == null || dto == null) {
            return user;
        }
        
        // Only update fields that are provided
        if (dto.getDisplayName() != null) {
            user.setDisplayName(dto.getDisplayName());
        }
        
        if (dto.getBio() != null) {
            user.setBio(dto.getBio());
        }
        
        if (dto.getLocation() != null) {
            user.setLocation(dto.getLocation());
        }
        
        if (dto.getWebsite() != null) {
            user.setWebsite(dto.getWebsite());
        }
        
        if (dto.getProfileImage() != null) {
            user.setProfileImage(dto.getProfileImage());
        }
        
        if (dto.getHeaderImage() != null) {
            user.setHeaderImage(dto.getHeaderImage());
        }
        
        return user;
    }
} 