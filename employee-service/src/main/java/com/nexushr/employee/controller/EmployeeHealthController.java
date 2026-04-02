package com.nexushr.employee.controller;

import com.nexushr.common.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeHealthController {

    @GetMapping("/health")
    public ApiResponse health() {
        return new ApiResponse("employee-service", "Employee service base module is running.");
    }
}
