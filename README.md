# Twitter Clone Application

This project is a Twitter clone application with a Spring Boot backend and a Next.js frontend.

## Project Structure

- `src/main/java`: Spring Boot application that provides RESTful API endpoints
- Frontend: Next.js application with Tailwind CSS for styling in the project root

## Getting Started

### Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- npm or yarn
- PostgreSQL database

### Running the Backend

1. Navigate to the project root directory
2. Run `./mvnw spring-boot:run` to start the Spring Boot application
3. The backend will be available at `http://localhost:8081/api`

### Running the Frontend

1. Navigate to the project root directory
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```
4. Open your browser and visit `http://localhost:3001` (or the port shown in the console output)

## Frontend-Backend Integration

The project uses Next.js API routes to proxy requests to the Spring Boot backend:

- Frontend API calls to `/api/*` are automatically proxied to the backend at `http://localhost:8081/api/*`
- This is configured in `next.config.js` using the rewrites feature
- The frontend services provide fallback to mock data when the backend is unreachable

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
- **Security**: JWT-based authentication with Spring Security
- **Messaging**: WebSockets with STOMP for real-time features
- **API Documentation**: SpringDoc with OpenAPI (Swagger)
- **Migration**: Liquibase for database schema migrations (previously Flyway)

### Frontend
- **Framework**: Next.js 14
- **Language**: TypeScript
- **Styling**: Tailwind CSS
- **State Management**: React Hooks
- **API Integration**: Fetch API with proxy configuration

### Testing
- **Unit Testing**: JUnit 5 with Mockito
- **Integration Testing**: Spring Boot Test
- **E2E Testing**: REST Assured for API testing
- **Performance Testing**: JMeter for load testing
- **Container Testing**: Testcontainers for integration tests

## Project Structure

The project follows standard Spring Boot project structure and best practices:

```
/
├── app/                    # Next.js app directory
│   ├── api/                # API route handlers  
│   ├── explore/            # Explore page
│   ├── users/              # User profiles
│   └── page.tsx            # Home page
├── components/             # React components
├── public/                 # Static assets
├── services/               # Frontend API services
├── src/                    # Spring Boot application
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── dhillon/
│   │   │           └── twitterclone/
│   │   │               ├── config/       # Configuration classes
│   │   │               ├── controller/   # REST API controllers
│   │   │               ├── dto/          # Data Transfer Objects
│   │   │               ├── entity/       # JPA entity classes
│   │   │               ├── exception/    # Custom exceptions
│   │   │               ├── repository/   # Spring Data repositories
│   │   │               ├── security/     # Security configurations
│   │   │               ├── service/      # Business logic
│   │   │               └── util/         # Utility classes
│   │   └── resources/
│   │       ├── application.yml          # Application configuration
│   │       ├── db/
│   │       │   └── changelog/           # Liquibase changelog files
│   │       └── static/                  # Static resources
│   └── test/                           # Test classes
├── next.config.js                      # Next.js configuration
├── package.json                        # Frontend dependencies
├── pom.xml                             # Backend dependencies
├── tsconfig.json                       # TypeScript configuration
└── README.md                           # This file
```

## API Documentation

The application uses Springdoc OpenAPI (formerly Swagger) for API documentation. Once the application is running, you can access:

- **Swagger UI**: Interactive API documentation
  ```
  http://localhost:8081/api/swagger-ui.html
  ```

- **OpenAPI Specification**: Raw OpenAPI JSON
  ```
  http://localhost:8081/api/api-docs
  ```

## License

This project is open source and available under the [MIT License](LICENSE).

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
- **Migration**: Liquibase for database schema migrations (previously Flyway)

### Testing
- **Unit Testing**: JUnit 5 with Mockito
- **Integration Testing**: Spring Boot Test
- **E2E Testing**: REST Assured for API testing
- **Performance Testing**: JMeter for load testing
- **Container Testing**: Testcontainers for integration tests

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
│       ├── db/
│       │   ├── changelog/            # Liquibase changelog files
│   │   │   ├── changes/          # Individual change sets
│   │   │   ├── sql/              # SQL-based migrations
│   │   │   └── db.changelog-master.yaml  # Master changelog
│   │   └── migration.backup/     # Backup of original Flyway migrations
│   └── static/                   # Static resources
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

### Running Tests

#### Unit and Integration Tests

To run all unit and integration tests:

```
./mvnw test
```

#### End-to-End Tests

The application includes E2E tests that test the complete API flow from external HTTP requests to database persistence. These tests are structured to be run in order and simulate a complete user journey through the API.

To run only the E2E tests:

```
./mvnw test -Pe2e-tests
```

The E2E tests cover:
- User Management
  - Checking username/email availability
  - User registration
  - Profile retrieval
  - User profile updates
  - User search
  - User deletion

Additional E2E tests for other features will be added in the future.

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

## Database Migrations

### Liquibase Migration

The application uses Liquibase for database schema migrations. Liquibase offers several advantages over the previously used Flyway:

1. **Multiple Format Support**: Supports XML, YAML, JSON, and SQL formats
2. **Enhanced Rollback**: Better support for rollback operations
3. **Preconditions**: Ability to specify preconditions for changesets
4. **Contexts**: Support for running different changes in different environments
5. **SQL Generation**: Ability to generate SQL scripts from changesets for DBA review

### Migration Structure

The migration files are organized as follows:

- `db.changelog-master.yaml`: The master changelog file that includes all other changesets
- `changes/`: Directory containing YAML-based changesets
- `sql/`: Directory containing SQL-based migrations

### Running Migrations

Migrations are automatically applied when the application starts. The configuration in `application.yml` controls this behavior:

```yaml
spring:
  liquibase:
    enabled: true
    change-log: classpath:db/changelog/db.changelog-master.yaml
    database-change-log-table: DATABASECHANGELOG
    database-change-log-lock-table: DATABASECHANGELOGLOCK
```

For different environments, the migration behavior can be customized:

- **Development**: Migrations are disabled by default to allow Hibernate to update the schema
- **Test**: Migrations are enabled to ensure consistent test data
- **Production**: Migrations are enabled to ensure controlled schema changes

### Adding New Migrations

To add a new migration:

1. Create a new changeset file in the `changes/` directory or a new SQL file in the `sql/` directory
2. Add the file to the master changelog (`db.changelog-master.yaml`)
3. Run the application to apply the migration 

## Recent Updates

### API Endpoint Improvements (March 2025)

We've made significant improvements to the API endpoints, focusing especially on the user management functionality:

1. **Fixed Ambiguous URL Mappings**: Resolved issues with conflicting request mappings in the UserController. The application now correctly handles both UUID-based and username-based user lookups.

2. **Improved Error Handling**: Enhanced error responses with detailed error messages and appropriate HTTP status codes.

3. **Added Follower Count Integration**: Updated the API to accurately retrieve follower and following counts using the FollowRepository.

4. **Username and Email Availability Checks**: Added dedicated endpoints to check if a username or email is already in use.

5. **Consolidated Controller Logic**: Simplified the controller by combining similar methods and improving code organization.

All API endpoints now have consistent behavior and properly follow RESTful principles.

### API Endpoints Testing Status

All endpoints have been tested and are working correctly:

- ✅ GET `/api/users` - Get all users
- ✅ GET `/api/users/{idOrUsername}` - Get user by ID or username
- ✅ PUT `/api/users/{idOrUsername}` - Update user
- ✅ DELETE `/api/users/{idOrUsername}` - Delete user
- ✅ POST `/api/users` - Create new user
- ✅ GET `/api/users/check-username?username={username}` - Check username availability
- ✅ GET `/api/users/check-email?email={email}` - Check email availability
- ✅ GET `/api/users/search?query={query}` - Search users 