CREATE TABLE performance_reviews (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL REFERENCES employees(id),
    reviewer_id BIGINT NOT NULL REFERENCES employees(id),
    review_period VARCHAR(100) NOT NULL,
    goals_summary TEXT,
    strengths TEXT,
    improvement_areas TEXT,
    manager_comments TEXT,
    goal_achievement_rating INTEGER NOT NULL CHECK (goal_achievement_rating BETWEEN 1 AND 5),
    teamwork_rating INTEGER NOT NULL CHECK (teamwork_rating BETWEEN 1 AND 5),
    punctuality_rating INTEGER NOT NULL CHECK (punctuality_rating BETWEEN 1 AND 5),
    overall_rating NUMERIC(4, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    reviewed_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_performance_reviews_employee_id ON performance_reviews(employee_id);
CREATE INDEX idx_performance_reviews_reviewer_id ON performance_reviews(reviewer_id);
