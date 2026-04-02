package com.nexushr.auth.repository;

import com.nexushr.auth.model.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);
}
