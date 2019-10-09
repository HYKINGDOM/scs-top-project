package com.scs.top.project.common.exception;

public class SessionNotFoundException extends Exception {

    protected String message;

    public SessionNotFoundException() {
        setMessage("Session is not found!");
    }

    public SessionNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
