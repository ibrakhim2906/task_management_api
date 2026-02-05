package com.ibrakhim2906.task_manager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @Email @NotBlank String email,
        @NotBlank String password
) { }
