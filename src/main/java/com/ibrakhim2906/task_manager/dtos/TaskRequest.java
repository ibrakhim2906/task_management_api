package com.ibrakhim2906.task_manager.dtos;

import com.ibrakhim2906.task_manager.models.enums.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank String details,
        @Future(message = "The due date should be set later")LocalDateTime dueDate,
        TaskStatus status
) { }
