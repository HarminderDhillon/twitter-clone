package com.example.twitterclone.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Test configuration for integration tests.
 */
@TestConfiguration
public class TestConfig {
    
    /**
     * Create PostgreSQL container for testing.
     * The container will be started when the bean is initialized.
     *
     * @return the PostgreSQL container
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:14"))
                .withDatabaseName("twitter_clone_test")
                .withUsername("testuser")
                .withPassword("testpass");
    }
    
    /**
     * Create a password encoder for testing.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 