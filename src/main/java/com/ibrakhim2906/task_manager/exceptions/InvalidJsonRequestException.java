package com.ibrakhim2906.task_manager.exceptions;

public class InvalidJsonRequestException extends RuntimeException{
    public InvalidJsonRequestException() { super("Invalid request body"); }
}
