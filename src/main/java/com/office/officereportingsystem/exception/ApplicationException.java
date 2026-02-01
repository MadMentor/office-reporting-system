package com.office.officereportingsystem.exception;

public abstract class ApplicationException extends RuntimeException {
    private final Object[] args;

    public ApplicationException(String messageCode, Object... args) {
        super(messageCode);
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }
}
