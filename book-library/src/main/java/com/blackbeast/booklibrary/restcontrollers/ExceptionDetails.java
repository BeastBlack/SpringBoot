package com.blackbeast.booklibrary.restcontrollers;

public class ExceptionDetails {
    private String exceptionName;
    private String message;

    public ExceptionDetails(String exceptionName, String message) {
        this.exceptionName = exceptionName;
        this.message = message;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
