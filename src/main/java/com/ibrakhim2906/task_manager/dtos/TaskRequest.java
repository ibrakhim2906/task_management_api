package com.ibrakhim2906.task_manager.dtos;

import com.ibrakhim2906.task_manager.models.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record TaskRequest(
    @NotBlank @Size(max = 1000) String details,
    LocalDateTime dueDate,
    TaskStatus status
) {}
