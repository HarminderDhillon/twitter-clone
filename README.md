# Twitter Clone

A Twitter clone application built with Spring Boot 3.x as the backend framework.

## Features

- User Management: Registration, authentication, profiles, follows
- Posts: Create, reply, repost, and like posts
- Hashtags: Automatic detection and indexing
- Timeline: Home timeline, user timeline, and explore feed
- Notifications: Real-time notifications for likes, replies, follows, etc.
- Search: Find users, posts, and hashtags
- Direct Messaging: One-on-one and group chats

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL 
- **ORM**: Spring Data JPA with Hibernate
- **Cache**: Redis for session management and caching
- **Search**: Elasticsearch for advanced search capabilities
- **Security**: JWT-based authentication with Spring Security
- **Messaging**: WebSockets with STOMP for real-time features
- **API Documentation**: SpringDoc with OpenAPI (Swagger)
- **Migration**: Flyway for database schema migrations

## Project Structure

The project follows standard Spring Boot project structure and best practices:

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── twitterclone/
│   │               ├── config/        # Configuration classes
│   │               ├── controller/    # REST API controllers
│   │               ├── dto/           # Data Transfer Objects
│   │               ├── entity/        # JPA entity classes
│   │               ├── exception/     # Custom exceptions
│   │               ├── repository/    # Spring Data repositories
│   │               ├── security/      # Security configurations
│   │               ├── service/       # Business logic
│   │               └── util/          # Utility classes
│   └── resources/
│       ├── application.yml           # Application configuration
│       ├── db/migration/             # Flyway migrations
│       └── static/                   # Static resources
└── test/
    └── java/
        └── com/
            └── example/
                └── twitterclone/     # Test classes
```

## Getting Started

### Prerequisites
- JDK 21 or later
- Maven 3.8 or later
- PostgreSQL 14 or later
- Redis 6.2 or later (optional for development)
- Elasticsearch 8.x (optional for development)
- RabbitMQ 3.9 or later (optional for development)

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/twitter-clone.git
cd twitter-clone
```

2. Configure database connection in `application.yml`

3. Build the application
```bash
./mvnw clean install
```

4. Run the application
```bash
./mvnw spring-boot:run
```

The application will be available at http://localhost:8080/api

## API Documentation

The application uses Springdoc OpenAPI (formerly Swagger) for API documentation. Once the application is running, you can access:

- **Swagger UI**: Interactive API documentation
  ```
  http://localhost:8080/api/swagger-ui.html
  ```

- **OpenAPI Specification**: Raw OpenAPI JSON
  ```
  http://localhost:8080/api/api-docs
  ```

The API documentation includes:

- Detailed endpoint descriptions
- Request and response schemas
- Authentication requirements
- Example values for testing
- API versioning information

You can test API endpoints directly from the Swagger UI interface. The documentation is automatically generated from the codebase annotations and configuration.

### API Endpoints Overview

The API is organized into the following main resources:

- **Users**: User management operations (`/api/users/*`)
  - Create, read, update, and delete users
  - Search for users
  - Check username/email availability

- **Posts**: Post management operations (`/api/posts/*`)
  - Create, read, update, and delete posts
  - Get user timeline, home timeline, and trending posts
  - Search for posts
  - Create replies and reposts

- **Authentication**: Authentication operations (`/api/auth/*`)
  - Login, logout, and token refresh
  - Password reset

- **Hashtags**: Hashtag operations (`/api/hashtags/*`)
  - Get trending hashtags
  - Get posts by hashtag

- **Notifications**: Notification operations (`/api/notifications/*`)
  - Get user notifications
  - Mark notifications as read

### Endpoint Health Summary

All endpoints have been tested and are working correctly. Here's a summary of the tested endpoints:

#### User Endpoints
1. ✅ **Check Username Availability** - `/api/users/check-username?username={username}`
   - Returns `true` if the username is available, `false` if it's taken
   
2. ✅ **Check Email Availability** - `/api/users/check-email?email={email}`
   - Returns `true` if the email is available, `false` if it's taken
   
3. ✅ **Search Users** - `/api/users/search?query={query}`
   - Returns a list of users matching the search query
   
4. ✅ **Get User by Username** - `/api/users/{username}`
   - Returns the user details for the specified username

#### Post Endpoints
1. ✅ **Get User Timeline** - `/api/posts/user/{username}`
   - Returns a paginated list of posts by the specified user
   
2. ✅ **Create Post** - `POST /api/posts/user/{username}`
   - Creates a new post for the specified user
   
3. ✅ **Get Post by ID** - `/api/posts/{id}`
   - Returns the post with the specified ID
   
4. ✅ **Update Post** - `PUT /api/posts/{id}`
   - Updates the post with the specified ID
   
5. ✅ **Search Posts** - `/api/posts/search?query={query}`
   - Returns a paginated list of posts matching the search query
   
6. ✅ **Get Posts by Hashtag** - `/api/posts/hashtag/{hashtag}`
   - Returns a paginated list of posts containing the specified hashtag
   
7. ✅ **Get Replies to Post** - `/api/posts/{id}/replies`
   - Returns a paginated list of replies to the specified post
   
8. ✅ **Create Reply** - `POST /api/posts/{parentId}/reply/{username}`
   - Creates a reply to the specified post
   
9. ✅ **Create Repost** - `POST /api/posts/{originalPostId}/repost/{username}`
   - Creates a repost of the specified post
   
10. ✅ **Get Trending Posts** - `/api/posts/trending`
    - Returns a paginated list of trending posts based on engagement metrics

## Development with Spring Boot DevTools

This project uses Spring Boot DevTools to enhance the development experience. DevTools provides several features:

### Automatic Restart
When you make changes to your code, DevTools will automatically restart your application. This is much faster than a full restart because DevTools uses two classloaders:
- A base classloader for unchanging classes (like libraries)
- A restart classloader for your application code

### Live Reload
DevTools includes a LiveReload server that can automatically trigger a browser refresh when resources are changed.

### Property Defaults
DevTools sets some properties differently in development to enhance the experience:
- Disables template caching
- Enables debug logging for the web group
- Enables H2 console if using H2 database

### Using DevTools
1. Run the application with the `dev` profile:
   ```bash
   ./mvnw spring-boot:run -Dspring.profiles.active=dev
   ```

2. Make changes to your code - the application will automatically restart
3. Access the API through Swagger UI or direct API calls
4. For optimal experience, install a LiveReload browser extension

## Testing

The application includes comprehensive tests at multiple levels:

### Unit Tests

To run unit tests:
```bash
./mvnw test -Dgroups="UnitTest"
```

These tests verify the functionality of individual components in isolation, using mocks for external dependencies.

### Repository Tests

To run repository tests:
```bash
./mvnw test -Dgroups="RepositoryTest"
```

These tests verify the interaction with the database using an in-memory H2 database.

### Controller Tests

To run controller tests:
```bash
./mvnw test -Dgroups="ControllerTest"
```

These tests verify the REST API endpoints using MockMvc.

### Integration Tests

To run integration tests:
```bash
./mvnw test -Dgroups="IntegrationTest"
```

These tests use TestContainers to start a real PostgreSQL database for testing the full application stack.

### All Tests

To run all tests:
```bash
./mvnw test
```

## Test Types

1. **Unit Tests**: Test individual components in isolation (e.g., services, utilities)
2. **Repository Tests**: Test data access and JPA queries using @DataJpaTest
3. **Controller Tests**: Test REST API endpoints using MockMvc
4. **Integration Tests**: Test the full application stack with TestContainers

## Environment Configuration

The application supports multiple environments:

- `dev`: Development mode with in-memory databases
- `test`: Testing mode with test containers
- `prod`: Production mode with optimized settings

To specify the environment:

```bash
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

## License

This project is licensed under the MIT License - see the LICENSE file for details. 