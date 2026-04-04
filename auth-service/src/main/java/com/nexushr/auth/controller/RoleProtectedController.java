package com.nexushr.auth.controller;

import com.nexushr.common.api.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/access")
public class RoleProtectedController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse adminOnly() {
        return new ApiResponse("auth-service", "Only ADMIN users can access this endpoint.");
    }

    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER')")
    public ApiResponse managerAccess() {
        return new ApiResponse("auth-service", "ADMIN and HR_MANAGER users can access this endpoint.");
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER', 'EMPLOYEE')")
    public ApiResponse currentUser(Authentication authentication) {
        return new ApiResponse("auth-service", "Authenticated user: " + authentication.getName());
    }
}
