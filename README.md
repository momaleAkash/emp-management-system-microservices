# Employee Management Microservices

A Spring Boot microservices project for employee management with centralized JWT authentication, synchronous gRPC communication, asynchronous Kafka events, and Docker-based local orchestration.

## Overview

This system is split into five services:

- `api-gateway` exposes a single entry point for clients and validates JWTs before forwarding protected requests.
- `security-service` handles login and token validation.
- `employee-service` manages employee CRUD operations.
- `performance-service` manages employee performance review records.
- `hr-analytics-service` consumes employee creation events from Kafka.

The project uses:

- Spring Boot
- Spring Cloud Gateway
- Spring Security
- Spring Data JPA
- PostgreSQL
- gRPC + Protocol Buffers
- Apache Kafka
- OpenAPI / Swagger
- Docker Compose

## Architecture

### Request flow

1. Client sends requests to `api-gateway`.
2. `api-gateway` routes `/auth/**` requests to `security-service`.
3. Protected routes such as `/api/employees/**` and `/api/performanceReviews/**` pass through a custom JWT validation filter.
4. `employee-service` persists employee data in PostgreSQL.
5. On employee creation, `employee-service` calls `performance-service` over gRPC to create a performance review.
6. `employee-service` then publishes an employee-created event to Kafka.
7. `hr-analytics-service` consumes the Kafka event for downstream processing.

### Communication patterns

- Client to services: REST through `api-gateway`
- Gateway to security: REST
- Employee to performance: gRPC
- Employee to analytics: Kafka events

## Services

### API Gateway

- Port: `4004`
- Routes requests to internal services
- Applies the custom `JwtValidation` filter to protected endpoints
- Exposes aggregated API docs routes:
  - `/api-docs/security`
  - `/api-docs/employees`
  - `/api-docs/performance`

### Security Service

- Port: `4005`
- Endpoints:
  - `POST /login`
  - `GET /validate`
- Stores users in PostgreSQL
- Generates and validates JWT tokens

### Employee Service

- Port: `4000`
- Endpoints under `/employees`
- Stores employee records in PostgreSQL
- On create:
  - saves employee
  - creates performance record by gRPC
  - publishes Kafka event
- On delete:
  - deletes employee
  - deletes performance record by gRPC

### Performance Service

- REST port: `4001`
- gRPC port: `9001`
- Endpoints under `/performanceReviews`
- Stores performance reviews in PostgreSQL

### HR Analytics Service

- Kafka consumer only
- Consumes employee events from topic `employee`

## Repository structure

```text
.
├── api-gateway/
├── employee-service/
├── hr-analytics-service/
├── performance-service/
├── security-service/
├── docker-compose.yml
├── Employee_Management_Service_API_Requests.postman_collection.json
└── README.md
```

## Authentication flow

### Login

1. Client sends `POST /auth/login` to `api-gateway`.
2. Gateway forwards the request to `security-service`.
3. `security-service` checks the user by email from the database.
4. If the password matches, it generates a JWT token and returns it.

Example request:

```http
POST http://localhost:4004/auth/login
Content-Type: application/json

{
  "email": "user1@example.com",
  "password": "user1"
}
```

### Token validation

1. Client sends a protected request with `Authorization: Bearer <token>`.
2. `api-gateway` intercepts the request.
3. The gateway calls `security-service /validate`.
4. If the token is valid, the original request is forwarded to the destination service.
5. If invalid, the gateway returns `401 Unauthorized`.

## Example protected APIs

### Employee APIs

- `GET /api/employees`
- `POST /api/employees`
- `PUT /api/employees/{id}`
- `DELETE /api/employees/{id}`

### Performance APIs

- `GET /api/performanceReviews`
- `GET /api/performanceReviews/{employeeId}`
- `PUT /api/performanceReviews/{employeeId}`

## Local setup

### Prerequisites

- Java 17
- Maven
- Docker
- Docker Compose

### Run with Docker Compose

From the project root:

```bash
docker-compose up --build
```

This starts:

- `api-gateway`
- `security-service`
- `employee-service`
- `performance-service`
- `hr-analytics-service`
- three PostgreSQL containers
- Kafka broker

To stop everything:

```bash
docker-compose down
```

## Ports

| Component | Port |
|---|---:|
| API Gateway | `4004` |
| Security Service | `4005` |
| Employee Service | `4000` |
| Performance Service REST | `4001` |
| Performance Service gRPC | `9001` |
| Employee DB | `5000` |
| Security DB | `5001` |
| Performance DB | `5002` |
| Kafka external | `9094` |

## Databases

Each core service has its own PostgreSQL database:

- `employee-service-db`
- `security-service-db`
- `performance-service-db`

This keeps service data isolated and closer to a real microservices design.

## Default seeded user

The `security-service` seeds a default user from `security-service/src/main/resources/data.sql`.

- Email: `user1@example.com`
- Password: `user1`
- Role: `ADMIN`

## API documentation

After the stack starts, OpenAPI docs can be accessed through the gateway:

- `http://localhost:4004/api-docs/security`
- `http://localhost:4004/api-docs/employees`
- `http://localhost:4004/api-docs/performance`

Project images are available in the `images/` folder:

- `images/Architecture_Diagram.png`
- `images/auth-controller-requests.png`
- `images/employee-controller-requests.PNG`
- `images/performance-controller-requests.PNG`

## Postman collection

The repo includes:

- `Employee_Management_Service_API_Requests.postman_collection.json`

Import it into Postman to test:

- login
- employee CRUD
- performance review APIs
- API docs routes

## Build and test

To build the full multi-module project:

```bash
mvn clean install
```

To run tests inside a service module, use its Maven wrapper. Example:

```bash
cd security-service
./mvnw test
```

## Notable implementation details

- JWT validation happens at the gateway, not inside every business service.
- Employee creation uses a mixed workflow:
  - synchronous gRPC for performance account creation
  - asynchronous Kafka for analytics/event processing
- `employee-service` includes rollback logic if part of the employee creation workflow fails after the employee record is saved.

## License

This project is licensed under the terms in [LICENSE](LICENSE).
