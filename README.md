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

## Day 4 Setup

Day 4 focused on creating the employee database schema and building employee CRUD operations.

Implemented in `employee-service`:

- PostgreSQL datasource configuration using `application.properties`
- Flyway migration setup for schema versioning
- Initial schema for `departments`, `roles`, `employees`, and `employee_roles`
- Seed data for default departments and employee roles
- JPA entities for `Department`, `Role`, and `Employee`
- Repositories for employee, department, and role access
- Employee create, read, update, and delete service logic
- REST endpoints for employee CRUD operations
- Exception handler for validation and request errors

Employee CRUD endpoints added:

- `GET /api/employees`
- `GET /api/employees/{id}`
- `POST /api/employees`
- `PUT /api/employees/{id}`
- `DELETE /api/employees/{id}`

Day 4 prepares the project for Day 5 Redis integration and attendance module foundation.

## Day 5 Setup

Day 5 focused on Redis-based session caching and the attendance tracking module foundation.

Implemented in `auth-service`:

- Redis configuration using `application.properties`
- Redis template configuration for session objects
- Session cache service for storing login session details by refresh token
- Session cache integration with login, refresh, and logout flows

Implemented in `employee-service`:

- Flyway migration for `attendance_records`
- Attendance JPA entity and status enum
- Attendance repository for employee attendance lookups
- Attendance service for check-in, check-out, and history retrieval
- Attendance REST controller for starter attendance APIs

Attendance endpoints added:

- `POST /api/attendance/check-in`
- `POST /api/attendance/{attendanceId}/check-out`
- `GET /api/attendance/employee/{employeeId}`

Day 5 prepares the project for Day 6 leave management and approval workflow.

## Day 6 Setup

Day 6 focused on leave management and approval workflow.

Implemented in `employee-service`:

- Flyway migration for the `leave_requests` table
- `LeaveRequest` entity with leave type, date range, status, and review metadata
- `LeaveType` and `LeaveStatus` enums
- Repository support for employee leave history and pending requests
- Leave management service for submit, approve, and reject actions
- Leave management REST controller for request and approval APIs

Leave management endpoints added:

- `POST /api/leaves`
- `GET /api/leaves/employee/{employeeId}`
- `GET /api/leaves/pending`
- `POST /api/leaves/{leaveRequestId}/approve`
- `POST /api/leaves/{leaveRequestId}/reject`

Day 6 prepares the project for Day 7 backend checkpoint and testing.

Git was intentionally not used as requested.
