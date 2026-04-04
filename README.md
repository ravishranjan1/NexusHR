# NexusHR Project Progress

This workspace contains the step-by-step backend implementation for the NexusHR platform.

## Day 1 Setup

Day 1 focused on the backend foundation.

Modules created:

- `api-gateway`
- `auth-service`
- `employee-service`
- `payroll-service`
- `common`

Day 1 setup includes:

- Java 21
- Spring Boot 3.3
- Maven multi-module structure
- `application.properties` in each service

## Day 2 Setup

Day 2 focused on the authentication domain model and security foundation.

Implemented in `auth-service`:

- `Employee` entity using JPA/Hibernate
- `Role` entity and `RoleName` enum for role-based access
- `EmployeeRepository` and `RoleRepository`
- JWT authentication filter foundation
- JWT service for token validation
- Spring Security stateless configuration
- Argon2 password encoder
- H2 datasource and JPA configuration in `application.properties`
- Initial seed data for default roles and admin user

Day 2 prepares the project for Day 3 authentication endpoints such as signup, login, refresh, and logout.

## Day 3 Setup

Day 3 focused on building real authentication APIs and role-based authorization.

Implemented in `auth-service`:

- `signup` endpoint for new employee registration
- `login` endpoint for email/password authentication
- `refresh` endpoint for generating a new access token using a refresh token
- `logout` endpoint for revoking a refresh token
- Refresh token entity and repository for persistent token handling
- Authentication service for signup, login, refresh, and logout business logic
- Exception handler for validation and authentication errors
- `@PreAuthorize` based role-protected endpoints
- JWT access token generation with user roles included in claims

Role-based access endpoints added:

- `/api/auth/access/admin`
- `/api/auth/access/manager`
- `/api/auth/access/me`

Day 3 prepares the project for Day 4 database schema and employee CRUD work.

Git was intentionally not used as requested.
