CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    employee_code VARCHAR(30) NOT NULL UNIQUE,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(20),
    job_title VARCHAR(120) NOT NULL,
    hire_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    department_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_employees_department
        FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE TABLE employee_roles (
    employee_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (employee_id, role_id),
    CONSTRAINT fk_employee_roles_employee
        FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE,
    CONSTRAINT fk_employee_roles_role
        FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO departments (code, name, description) VALUES
('HR', 'Human Resources', 'Handles employee lifecycle and people operations'),
('ENG', 'Engineering', 'Builds and maintains product features'),
('FIN', 'Finance', 'Manages payroll, budgeting, and reporting');

INSERT INTO roles (name) VALUES
('ADMIN'),
('HR_MANAGER'),
('EMPLOYEE');
