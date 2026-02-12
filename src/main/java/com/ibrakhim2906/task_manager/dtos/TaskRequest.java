package com.ibrakhim2906.task_manager.dtos;

import com.ibrakhim2906.task_manager.models.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank String details,
        LocalDateTime dueDate,
        TaskStatus status
) { }
