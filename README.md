# Secure Spring Boot API

Hi, I'm Furkan. This is a backend project I created to better understand how authentication works using Java and Spring Boot.

The main purpose of this application is to build a secure, testable REST API that handles login, registration, JWT authentication, token refreshing, and blacklist management. I also added a simple tutorial system to mimic real-world functionality.

# What's Included

This project provides:

- User registration and login
- JWT authentication (access & refresh tokens)
- Token blacklist system for logout
- MongoDB integration for storing user and tutorial data
- RESTful CRUD operations on a tutorial entity
- Swagger UI for API documentation and testing
- Docker Compose for containerized setup
- Environment configuration via .env
- Unit testing with JUnit and Mockito

# Testing

The service layer has been covered with unit tests written in JUnit and Mockito. I tried to follow a test-driven approach especially around authentication logic. It helped me think more clearly and keep the implementation focused.

# Folder Structure

```
src
├── main
│   ├── java
│   │   └── com.furkan.tutorials
│   │       ├── controller
│   │       │   ├── AuthController.java
│   │       │   └── TutorialController.java
│   │       ├── dto
│   │       │   ├── ApiResponse.java
│   │       │   ├── LoginResponse.java
│   │       │   └── RegisterRequest.java
│   │       ├── exception
│   │       │   └── GlobalExceptionHandler.java
│   │       ├── model
│   │       │   ├── User.java
│   │       │   ├── Tutorial.java
│   │       │   └── RefreshToken.java
│   │       ├── repository
│   │       │   ├── UserRepository.java
│   │       │   ├── TutorialRepository.java
│   │       │   └── RefreshTokenRepository.java
│   │       ├── security
│   │       │   ├── JwtFilter.java
│   │       │   ├── JwtUtil.java
│   │       │   └── SecurityConfig.java
│   │       ├── service
│   │       │   ├── AuthService.java
│   │       │   ├── AuthServiceImpl.java
│   │       │   ├── RefreshTokenService.java
│   │       │   ├── RefreshTokenServiceImpl.java
│   │       │   ├── TokenBlacklistService.java
│   │       │   ├── TokenBlacklistServiceImpl.java
│   │       │   ├── TutorialService.java
│   │       │   └── TutorialServiceImpl.java
│   │       └── TutorialsApplication.java
│   └── resources
│       ├── application.properties
│       └── static (if needed)
├── test
│   └── java
│       └── com.furkan.tutorials
│           └── service
│               ├── AuthServiceImplTest.java
│               ├── RefreshTokenServiceTest.java
│               └── TutorialServiceTest.java
.env
docker-compose.yml
README.md
.gitignore
```


# How to Run

To run the application with Docker, use the following command:

```
docker-compose up --build
```

Before running, create a `.env` file in the root directory with the following variables:

```
MONGO_URI=your_mongodb_connection_string
JWT_SECRET=your_jwt_secret_key
```

# API Documentation

After starting the application, you can access Swagger UI at:

http://localhost:8080/swagger-ui/index.html

# Final Notes

This project was built as a learning experience. My goal was to understand how to structure a backend service that is secure, well-documented, and tested. I'm still improving it and open to feedback.

- Furkan
