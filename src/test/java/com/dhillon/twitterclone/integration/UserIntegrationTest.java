package com.dhillon.twitterclone.integration;

import com.dhillon.twitterclone.dto.UserDto;
import com.dhillon.twitterclone.entity.User;
import com.dhillon.twitterclone.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the User API.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private User testUser;
    
    @BeforeEach
    public void setup() {
        // Clear all existing users
        userRepository.deleteAll();
        
        // Create a test user
        testUser = new User();
        testUser.setUsername("integrationtest");
        testUser.setEmail("integration@example.com");
        testUser.setPasswordHash(passwordEncoder.encode("password123"));
        testUser.setDisplayName("Integration Test");
        testUser.setBio("Bio for integration test");
        testUser.setEnabled(true);
        
        testUser = userRepository.save(testUser);
    }
    
    @Test
    public void getUserByUsername_ReturnsUser() throws Exception {
        mockMvc.perform(get("/api/users/{username}", testUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(testUser.getUsername())))
                .andExpect(jsonPath("$.email", is(testUser.getEmail())))
                .andExpect(jsonPath("$.displayName", is(testUser.getDisplayName())))
                .andExpect(jsonPath("$.bio", is(testUser.getBio())));
    }
    
    @Test
    public void createUser_WithValidData_CreatesUserAndReturnsIt() throws Exception {
        UserDto newUserDto = new UserDto();
        newUserDto.setUsername("newintuser");
        newUserDto.setEmail("newint@example.com");
        newUserDto.setPassword("password123");
        newUserDto.setDisplayName("New Integration User");
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(newUserDto.getUsername())))
                .andExpect(jsonPath("$.email", is(newUserDto.getEmail())))
                .andExpect(jsonPath("$.displayName", is(newUserDto.getDisplayName())));
        
        // Verify the user was actually created in the database
        assertThat(userRepository.findByUsername("newintuser")).isPresent();
    }
    
    @Test
    public void updateUser_WithValidData_UpdatesUserAndReturnsIt() throws Exception {
        UserDto updateDto = new UserDto();
        updateDto.setDisplayName("Updated Display Name");
        updateDto.setBio("Updated bio for integration test");
        
        mockMvc.perform(put("/api/users/{username}", testUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName", is(updateDto.getDisplayName())))
                .andExpect(jsonPath("$.bio", is(updateDto.getBio())));
        
        // Verify the user was actually updated in the database
        User updatedUser = userRepository.findById(testUser.getId()).orElseThrow();
        assertThat(updatedUser.getDisplayName()).isEqualTo("Updated Display Name");
        assertThat(updatedUser.getBio()).isEqualTo("Updated bio for integration test");
    }
    
    @Test
    public void deleteUser_RemovesUserFromDatabase() throws Exception {
        mockMvc.perform(delete("/api/users/{username}", testUser.getUsername()))
                .andExpect(status().isNoContent());
        
        // Verify the user was actually deleted from the database
        assertThat(userRepository.findById(testUser.getId())).isEmpty();
    }
    
    @Test
    public void searchUsers_FindsMatchingUsers() throws Exception {
        // Create a few more users for testing search
        User user1 = new User();
        user1.setUsername("searchtest1");
        user1.setEmail("search1@example.com");
        user1.setPasswordHash(passwordEncoder.encode("password"));
        userRepository.save(user1);
        
        User user2 = new User();
        user2.setUsername("searchtest2");
        user2.setEmail("search2@example.com");
        user2.setPasswordHash(passwordEncoder.encode("password"));
        userRepository.save(user2);
        
        mockMvc.perform(get("/api/users/search").param("query", "search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].username", hasItems("searchtest1", "searchtest2")));
    }
    
    @Test
    public void checkUsername_WithAvailableUsername_ReturnsTrue() throws Exception {
        mockMvc.perform(get("/api/users/check-username").param("username", "available"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
    
    @Test
    public void checkUsername_WithExistingUsername_ReturnsFalse() throws Exception {
        mockMvc.perform(get("/api/users/check-username").param("username", testUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
} 