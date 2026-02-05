package com.ibrakhim2906.task_manager.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
        @NotBlank String email,
        @NotBlank String password
) { }
