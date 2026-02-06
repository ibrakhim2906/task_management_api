package com.ibrakhim2906.task_manager.controllers;

import com.ibrakhim2906.task_manager.dtos.LoginRequest;
import com.ibrakhim2906.task_manager.dtos.LoginResponse;
import com.ibrakhim2906.task_manager.dtos.RegisterRequest;
import com.ibrakhim2906.task_manager.dtos.RegisterResponse;
import com.ibrakhim2906.task_manager.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService=authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest req) {
        return authService.register(req.email(), req.password());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req.email(), req.password());
    }
}