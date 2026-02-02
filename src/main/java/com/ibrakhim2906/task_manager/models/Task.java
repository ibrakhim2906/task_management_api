package com.ibrakhim2906.task_manager.models;

public class Task {

    private String details;
    private Long id;

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

    public void setId(Long id) {
        this.id=id;
    }

    public void setDetails(String details) {
        this.details=details;
    }
}
