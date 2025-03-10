package com.example.twitterclone.util;

import com.example.twitterclone.dto.PostDto;
import com.example.twitterclone.entity.Hashtag;
import com.example.twitterclone.entity.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for mapping between Post entities and DTOs.
 */
public class PostMapper {
    
    /**
     * Convert a Post entity to a PostDto.
     *
     * @param post the post entity
     * @return the post DTO
     */
    public static PostDto toDto(Post post) {
        if (post == null) {
            return null;
        }
        
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setUserId(post.getUser().getId());
        dto.setUsername(post.getUser().getUsername());
        dto.setUserDisplayName(post.getUser().getDisplayName() != null ? 
                post.getUser().getDisplayName() : post.getUser().getUsername());
        dto.setUserProfileImage(post.getUser().getProfileImage());
        dto.setContent(post.getContent());
        dto.setMedia(post.getMedia());
        dto.setReply(post.isReply());
        
        if (post.getParent() != null) {
            dto.setParentId(post.getParent().getId());
        }
        
        dto.setRepost(post.isRepost());
        
        if (post.getOriginalPost() != null) {
            dto.setOriginalPostId(post.getOriginalPost().getId());
        }
        
        // Convert hashtags to strings
        List<String> hashtagNames = post.getHashtags().stream()
                .map(Hashtag::getName)
                .collect(Collectors.toList());
        dto.setHashtags(hashtagNames);
        
        dto.setLikeCount(post.getLikeCount());
        dto.setReplyCount(post.getReplyCount());
        dto.setRepostCount(post.getRepostCount());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        
        return dto;
    }
    
    /**
     * Convert a list of Post entities to a list of PostDtos.
     *
     * @param posts the list of post entities
     * @return the list of post DTOs
     */
    public static List<PostDto> toDtoList(List<Post> posts) {
        if (posts == null) {
            return new ArrayList<>();
        }
        
        return posts.stream()
                .map(PostMapper::toDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Convert a PostDto to a Post entity.
     * Note: This does not set the user, parent, or original post references.
     * Those must be set separately.
     *
     * @param dto the post DTO
     * @return the post entity
     */
    public static Post toEntity(PostDto dto) {
        if (dto == null) {
            return null;
        }
        
        Post post = new Post();
        post.setContent(dto.getContent());
        post.setMedia(dto.getMedia());
        
        return post;
    }
    
    /**
     * Update a Post entity with data from a PostDto.
     * Only updates fields that are allowed to be updated.
     *
     * @param post the post entity to update
     * @param dto the post DTO with updated data
     * @return the updated post entity
     */
    public static Post updateEntity(Post post, PostDto dto) {
        if (post == null || dto == null) {
            return post;
        }
        
        // Only content can be updated
        if (dto.getContent() != null) {
            post.setContent(dto.getContent());
        }
        
        return post;
    }
} 