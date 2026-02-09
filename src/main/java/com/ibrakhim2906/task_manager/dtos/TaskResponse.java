package com.ibrakhim2906.task_manager.dtos;

import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.models.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String details,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime dueDate
) { public static TaskResponse from(Task t){
    return new TaskResponse(
            t.getId(),
            t.getDetails(),
            t.getStatus(),
            t.getCreatedAt(),
            t.getDueDate());
    }
}