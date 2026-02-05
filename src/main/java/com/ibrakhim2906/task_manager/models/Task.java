package com.ibrakhim2906.task_manager.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime dueDate;

    @PrePersist
    public void prePersist() {
        this.createdAt= LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt=LocalDateTime.now();
    }

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

    public User getOwner() {    return owner;}

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

    public void setCompleted(boolean status) {
        this.completed=status;
    }

    public void setOwner(User owner) { this.owner = owner;}
}
