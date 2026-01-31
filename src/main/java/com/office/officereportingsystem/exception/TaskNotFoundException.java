package com.office.officereportingsystem.exception;

public class TaskNotFoundException extends RuntimeException{

    private final String messageCode;

    public TaskNotFoundException(String messageCode) {
        super(messageCode);
        this.messageCode = messageCode;
    }
    public String getMessageCode() {
        return messageCode;
    }
}
