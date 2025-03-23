# Museum Backend API

This is the backend API for the Museum application, built with Ktor and PostgreSQL.

## Prerequisites

- JDK 11 or higher
- PostgreSQL 12 or higher
- Gradle 7.0 or higher

## Setup

### Database Setup

1. Create a PostgreSQL database:

```sql
CREATE DATABASE museum;
```

2. The application will automatically create the necessary tables on startup.

### Configuration

The application is configured using the `application.conf` file in the `src/main/resources` directory. You can override these settings using environment variables:

- `PORT`: The port the server will listen on (default: 8080)
- `DATABASE_URL`: JDBC URL for the PostgreSQL database (default: jdbc:postgresql://localhost:5432/museum)
- `DATABASE_USER`: PostgreSQL username (default: postgres)
- `DATABASE_PASSWORD`: PostgreSQL password (default: postgres)
- `DATABASE_MAX_POOL_SIZE`: Maximum database connection pool size (default: 3)
- `JWT_SECRET`: Secret key for JWT token generation (default: secret)
- `JWT_ISSUER`: Issuer for JWT tokens (default: http://0.0.0.0:8080/)
- `JWT_AUDIENCE`: Audience for JWT tokens (default: http://0.0.0.0:8080/museum)

## Running the Application

### From the Command Line

```bash
cd backend
./gradlew run
```

The server will start on http://0.0.0.0:8080.

### Development Mode

To run the application in development mode with hot reload:

```bash
./gradlew -t run
```

## API Endpoints

### Exhibits

- `GET /api/exhibits`: Get all exhibits
- `GET /api/exhibits/{id}`: Get a specific exhibit
- `POST /api/exhibits`: Create a new exhibit
- `PUT /api/exhibits/{id}`: Update an exhibit
- `DELETE /api/exhibits/{id}`: Delete an exhibit

### Events

- `GET /api/events`: Get all events
- `GET /api/events/{id}`: Get a specific event
- `POST /api/events`: Create a new event
- `PUT /api/events/{id}`: Update an event
- `DELETE /api/events/{id}`: Delete an event

### Visitor Information

- `GET /api/visitor-info`: Get visitor information
- `PUT /api/visitor-info`: Update visitor information

### Authentication

- `POST /api/auth/login`: Authenticate a user (login or register)
- `GET /api/auth/user`: Get current user info
- `POST /api/auth/logout`: Logout

### Contact

- `POST /api/contact`: Submit a contact form
- `GET /api/contact`: Get all contact submissions (admin only)
- `PUT /api/contact/{id}/read`: Mark a contact submission as read (admin only)

## Integration with Frontend

The backend is designed to work with the Museum frontend application. To connect the frontend to this backend:

1. Update the frontend API service to point to this backend instead of using mock data.
2. Ensure CORS is properly configured to allow requests from the frontend origin.