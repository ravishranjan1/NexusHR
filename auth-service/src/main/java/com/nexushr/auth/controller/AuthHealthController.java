package com.nexushr.auth.controller;

import com.nexushr.common.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthHealthController {

    @GetMapping("/health")
    public ApiResponse health() {
        return new ApiResponse("auth-service", "Auth service is ready for Day 2 security work.");
    }
}
