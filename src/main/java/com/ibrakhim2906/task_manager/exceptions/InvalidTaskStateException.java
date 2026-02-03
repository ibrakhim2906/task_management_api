package com.ibrakhim2906.task_manager.exceptions;

public class InvalidTaskStateException extends RuntimeException{
    public InvalidTaskStateException(Long id) {
        super("Task ID '"+id+"' current state do not allow that action");
    }
}
