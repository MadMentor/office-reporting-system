package com.office.officereportingsystem.exception;

public class ProjectNotFoundException extends RuntimeException{

    private final String messageCode;

    public ProjectNotFoundException(String messageCode) {
        super(messageCode);
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
