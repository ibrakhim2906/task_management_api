package com.ibrakhim2906.task_manager.dtos;

import com.ibrakhim2906.task_manager.models.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank String details,
        @NotNull LocalDateTime dueDate,
        @NotNull TaskStatus status
) { }
