
# Basic Auth Service

A Kotlin-based microservice implementing basic authentication features, such as user registration, login, and role-based access control. This project leverages Spring Boot, JWT authentication, and Docker for containerization.

---

## Features

- **User Registration**: Register new users with email and password.
- **User Login**: Authenticate users and provide JWT tokens.
- **Role-Based Access Control**: Permissions are managed using roles.
- **API Documentation**: Automatically generated Swagger/OpenAPI docs.
- **Containerization**: Docker support for easy deployment.

---

## Prerequisites

Before getting started, ensure you have the following installed:

- **Java 17+**: Required for Kotlin and Spring Boot.
- **Docker & Docker Compose**: For containerization and running the service.
- **Gradle**: The project uses Gradle for dependency management and builds (a wrapper is included).

---

## Getting Started

Follow these steps to set up and run the project locally or with Docker.

### 1. Clone the Repository

Clone this repository to your local machine and navigate to the project directory:

```bash
git clone <repository-url>
cd basic-auth-service
```

### 2. Build the Project

Use the Gradle wrapper to build the project:

```bash
./gradlew build
```

### 3. Run Locally

To run the application locally using the embedded Spring Boot server:

```bash
./gradlew bootRun
```

The service will be available at `http://localhost:8080`.

### 4. Run with Docker

Build and run the service using Docker Compose:

```bash
docker-compose up --build
```

### 5. Access API Documentation

When the service is running, API documentation is available at:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

---

## Configuration

The application uses the following configuration hierarchy:

1. **Base Configuration** (`application.yaml`)
2. **Environment-specific Overrides**:
   - `application-local.yaml`: For local development.
   - `application-production.yaml`: For production deployment.

Configuration files are located in `src/main/resources`.

**Environment Variables**: Certain properties, such as database credentials, can be overridden using environment variables.

---

## API Endpoints

Here are some of the core API endpoints:

### Authentication

- `POST /auth/register`: Register a new user.
  - **Request Body**:
    ```json
    {
      "email": "example@example.com",
      "password": "password123"
    }
    ```
  - **Response**:
    ```json
    {
      "id": 1,
      "email": "example@example.com",
      "roles": ["USER"]
    }
    ```

- `POST /auth/login`: Log in and obtain a JWT token.
  - **Request Body**:
    ```json
    {
      "email": "example@example.com",
      "password": "password123"
    }
    ```
  - **Response**:
    ```json
    {
      "token": "your-jwt-token"
    }
    ```

### User Management

- `GET /users`: Retrieve all users (admin access required).
- `GET /users/{id}`: Retrieve a specific user by ID.

---

## Database

The project uses a relational database. Below are the details:

### Default Setup

- **Local Environment**: H2 in-memory database.
- **Production Environment**: Configurable database (e.g., PostgreSQL, MySQL).

### Migrations

The project uses **Liquibase** for managing database migrations. Changelogs are defined in `src/main/resources/db/changelog`.

To apply migrations manually, use the following command:

```bash
./gradlew update
```

---

## Build Details

- **Language**: Kotlin
- **Framework**: Spring Boot
- **Build Tool**: Gradle
- **Dependencies**:
  - Spring Boot Starter Web
  - Spring Boot Starter Security
  - JWT (JSON Web Tokens)
  - Liquibase (Database Migrations)
  - H2 (Development Database)
  - Swagger/OpenAPI

---

## Testing

Run the tests using the following command:

```bash
./gradlew test
```

Test reports will be available in the `build/reports/tests/test` directory.

---

## Deployment

The service is containerized using Docker, enabling seamless deployment in various environments. Modify the `docker-compose.yaml` file to suit your production setup.

To deploy to a cloud provider (e.g., AWS, GCP, Azure), build the Docker image and push it to your container registry:

```bash
docker build -t your-repo/basic-auth-service .
docker push your-repo/basic-auth-service
```

---

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork this repository.
2. Create a feature branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m "Add feature name"`.
4. Push to your branch: `git push origin feature-name`.
5. Open a pull request.

---

## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

## Contact

For questions or support, contact the maintainers at:

- **Email**: support@example.com
- **GitHub Issues**: [Open an Issue](https://github.com/<repo-owner>/basic-auth-service/issues)
