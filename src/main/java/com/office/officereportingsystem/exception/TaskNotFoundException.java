package com.office.officereportingsystem.exception;

public class TaskNotFoundException extends ApplicationException{

    private final Object data;

    public TaskNotFoundException() {
        super("TASK_NOT_FOUND");
        this.data = null;
    }

    public TaskNotFoundException(Object task) {
        super("TASK_NOT_FOUND");
        this.data = task;
    }

    public Object getData() {
        return data;
    }
}
