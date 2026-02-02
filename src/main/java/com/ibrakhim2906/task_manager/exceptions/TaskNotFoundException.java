package com.ibrakhim2906.task_manager.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(Long id) {
        super("Task with id "+id+" not found");
    }
}
