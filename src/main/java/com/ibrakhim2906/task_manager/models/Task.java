package com.ibrakhim2906.task_manager.models;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class Task {
    @NotBlank
    private String details;

    private Long id;
    private boolean completed;
    private LocalDateTime createdAt;

    @Future(message = "The due date should be set later")
    private LocalDateTime dueDate;

    public Task() {
    }

    public Task(String details) {
        this.details=details;
    }

    public Long getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setDetails(String details) {
        this.details=details;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt=createdAt;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate=dueDate;
    }

    public void setStatus(boolean status) {
        this.completed=status;
    }
}
