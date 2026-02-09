package com.ibrakhim2906.task_manager.models;

import com.ibrakhim2906.task_manager.models.enums.TaskStatus;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

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
        this.updatedAt= LocalDateTime.now();
        if (this.status==null) {
            this.status=TaskStatus.TODO;
        }
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

    public TaskStatus getStatus() {
        return status;
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

    public void setStatus(TaskStatus status) {
        if (status == TaskStatus.IN_PROGRESS) {
            this.setInProgress();
        } else if (status == TaskStatus.DONE) {
            this.setCompleted();
        } else if (status == TaskStatus.TODO) {
            this.setToDo();
        }
    }

    public void setCompleted() {
        status=TaskStatus.DONE;
    }

    public void setInProgress() {
        status=TaskStatus.IN_PROGRESS;
    }

    public void setToDo() {
        status=TaskStatus.TODO;
    }

    public void setOwner(User owner) { this.owner = owner;}
}
