package com.viperexz.backend.exception;

public class GlobalExceptionHandler extends RuntimeException {

    public GlobalExceptionHandler(String message) {
        super(message);
    }

    public GlobalExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "Error: " + super.getMessage();
    }
}
