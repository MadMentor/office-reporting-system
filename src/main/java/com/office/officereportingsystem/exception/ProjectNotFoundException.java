package com.office.officereportingsystem.exception;

public class ProjectNotFoundException extends ApplicationException{

    private final Object data;

    public ProjectNotFoundException() {
        super("PROJECT_NOT_FOUND");
        this.data = null;
    }

    public ProjectNotFoundException(Object data) {
        super("PROJECT_NOT_FOUND");
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
