CREATE TABLE leave_requests (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    leave_type VARCHAR(30) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    requested_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reviewed_at TIMESTAMP WITH TIME ZONE,
    reviewed_by VARCHAR(120),
    review_comments VARCHAR(255),
    CONSTRAINT fk_leave_requests_employee
        FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);
