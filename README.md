# Social Network Backend

A robust backend API for a social network application, built with Java and Spring Boot. This system provides core social networking features including user authentication, content management, messaging, and community interactions.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Configuration](#configuration)
- [Installation](#installation)
- [Database](#database)
- [API Documentation](#api-documentation)

## Overview
This is a RESTful API designed to power a social network platform. The system uses Spring Boot for the application framework, Spring Security for authentication, and PostgreSQL for persistent data storage. It is containerized using Docker to ensure consistent deployment environments, providing a scalable foundation for client applications.

## Features
### Authentication & Security
- Secure user login and registration
- JWT (JSON Web Token) based authentication with expiration
- Password encryption (BCrypt)

### User Management
- User profile creation and updates
- Avatar upload and management
- Subscription system (Follow/Unfollow users)

### Content Management
- **Posts**: Create, read, update, and delete posts
- **Media**: Image upload and handling for posts
- **Interactions**: Like/Unlike posts functionality
- **Comments**: Commenting system for posts

### Messaging
- Direct messaging between users
- Message history retrieval

### Administration
- Administrative capabilities for content and user oversight

## Technology Stack
### Backend Framework
- **Java 17** - Programming language
- **Spring Boot 3.5.3** - Application framework
- **Spring Security** - Authentication and access control
- **Spring Data JPA** - Persistence abstraction
- **Spring Validation** - Bean validation integration

### Database & Storage
- **PostgreSQL 17.6** - Primary relational database
- **Flyway 11.11.2** - Database migration and version control
- **Local File System** - Strategy for storing user-uploaded media (images)

### Security
- **JJWT 0.12.5** - JSON Web Token implementation for stateless authentication

### DevOps & Tools
- **Docker** - Containerization platform
- **Docker Compose** - Multi-container orchestration
- **Maven** - Dependency management and build tool
- **Lombok** - Boilerplate code reduction

## Architecture
The application follows a layered architecture utilizing the Facade pattern to separate concerns:

- **Controller Layer**: Handles incoming HTTP REST requests and validation.
- **Facade Layer**: Orchestrates complex business flows and converts between Entites and DTOs.
- **Service Layer**: Implements core business logic and transaction management.
- **Repository Layer**: Manages direct data access using Spring Data JPA repositories.
- **DTOs (Data Transfer Objects)**: Defines the structure for API requests and responses, decoupling the API contract from the database schema.

## Prerequisites
- **Java 17 SDK** (for local development)
- **Maven** (for local building)
- **Docker & Docker Compose** (for containerized deployment)

## Configuration
The application requires specific environment settings to function correctly.

### Environment Variables
Create a `.env` file in the root directory (used by Docker Compose). It must contain the following variables:

```bash
# Database Connection
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_db:5432/social-network-db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_db_password

# Security
SSL_PASSWORD=your_keystore_password
```

### SSL Configuration
The application is configured to run securely over HTTPS.
- **Keystore Path**: The application expects a PKCS12 keystore at `/etc/ssl/mydomain/keystore.p12` within the container.
- **Volume Mapping**: Ensure your host's SSL directory is mapped correctly in `docker-compose.yml`.

## Installation

### Running with Docker (Recommended)
1. **Prepare Environment**: Ensure your `.env` file and SSL certificates are in place.
2. **Build and Run**:
   ```bash
   docker-compose up -d --build
   ```
3. **Access**: The API will be available at `https://localhost:443`.
   - *Note*: Port 443 on the host maps to port 8443 in the container.
4. **Data Persistence**:
   - `postgres-data` volume persists database records.
   - `./app-data` directory on host persists uploaded images from `/data` in the container.

### Running Locally
1. Start your local PostgreSQL instance and create a database (e.g., `social-network-db`).
2. Configure `src/main/resources/application.properties` to match your local database credentials and SSL path.
3. Run the application using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

## Database
Database schema changes are managed via **Flyway**.
- **Migration Scripts**: `src/main/resources/db/migration`
- **Schema Diagram**: [View Database Schema](https://drive.google.com/file/d/1wz2le8OR5H1K4kI6Wbgn86-djLPK422Y/view?usp=sharing)

## API Documentation
The API adheres to standard REST principles. All endpoints are prefixed with `/api/v1`.

- **Testing**: A Postman collection is included in the project for testing API endpoints.
    - File: `Social Network.postman_collection.json`

