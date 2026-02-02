package com.ibrakhim2906.task_manager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
public class Task {
    @Column
    @NotBlank
    private String details;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean completed;

    @Column
    private LocalDateTime createdAt;

    @Column
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
