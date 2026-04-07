package com.nexushr.employee.repository;

import com.nexushr.employee.model.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmployeeCode(String employeeCode);

    @Override
    @EntityGraph(attributePaths = {"department", "roles"})
    List<Employee> findAll();

    @EntityGraph(attributePaths = {"department", "roles"})
    Optional<Employee> findById(Long id);
}
