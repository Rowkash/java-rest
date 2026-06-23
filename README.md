# Authentication Service

A pet project built with **Java Spring Boot** to implement user authentication, token management,
and session handling. The project follows a feature-based structure and is designed to serve as an
authentication service for applications that require secure user access.

## Features

* **User Authentication** — User registration, login, and logout.
* **JWT Authentication** — Access tokens and UUID-based refresh tokens stored in HTTP cookies.
* **Token Refreshing** — Endpoint for refreshing authentication tokens.
* **Session Management** — User sessions stored in Redis.
* **Spring Security** — Security configuration with a custom filter for extracting JWT tokens from
  cookies.
* **API Documentation** — Swagger / OpenAPI integration.
* **Database** — PostgreSQL for persistent data storage.
* **Docker Compose** — PostgreSQL container setup for local development.
* **Feature-Based Architecture** — Application code organized into functional modules.

## Tech Stack

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Maven
* PostgreSQL
* Redis
* JWT
* Swagger / OpenAPI
* Docker Compose

## Project Structure

The application follows a feature-based structure, separating business functionality into
independent modules.

```text
src/java/com/rowkash/portfolios/api
├── config/
├── users/
├── auth/
├── sessions/
├── auth/
├── sessions/
├── security/
└── common/
```

* **`users`** — User-related functionality.
* **`auth`** — Authentication flows, including registration, login, logout, and token refreshing.
* **`sessions`** — User session management using Redis.
* **`config`** — Application configuration, including API documentation, security, and session
  settings.

## API Endpoints

| Method | Endpoint               | Description                     |
|--------|------------------------|---------------------------------|
| POST   | `/auth/register`       | Register a new user             |
| POST   | `/auth/login`          | Authenticate a user             |
| DELETE | `/auth/logout`         | Log out the current user        |
| POST   | `/auth/refresh-tokens` | Refresh authentication tokens   |
| GET    | `/users/me`            | Get self user data              |
| GET    | `/users/`              | Get users' page with pagination |

Authentication tokens are delivered through HTTP cookies.

## Getting Started

### Prerequisites

Make sure you have the following installed:

* Java (version compatible with the project)
* Maven
* Docker and Docker Compose
* Redis

### 1. Clone the Repository

```bash
git clone <repository-url>
cd <project-directory>
```

### 2. Configure the Application

Local development settings are currently defined in `application-local.properties`.

Update the database, Redis, and other required configuration values to match your local environment.

> **Note:** Configuration values are hardcoded for simplicity during development. For production
> use, sensitive values should be provided through environment variables or a dedicated secrets
> management solution.

### 3. Start PostgreSQL and Redis

Start the PostgreSQL and Redis containers using Docker Compose:

```bash
docker compose up -d
```

### 4. Run the Application

Start the application using Maven:

```bash
mvn spring-boot:run
```

Alternatively, build the project and run the generated JAR:

```bash
mvn clean package
java -jar target/<application-name>.jar
```

## API Documentation

The API documentation is available through the integrated Swagger / OpenAPI configuration.

Once the application is running, open the Swagger UI endpoint configured in the project. A common
default is:

```text
http://localhost:4000/swagger-ui/index.html#/
```

The actual URL may vary depending on the application configuration.

## Future Improvements

* Add a `portfolios` module for managing user portfolios.
* Support uploading portfolio files and associating them with portfolios.
* Improve application configuration by moving hardcoded values to environment variables.
* Extend automated testing and improve error handling.
