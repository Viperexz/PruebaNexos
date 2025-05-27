package com.viperexz.backend.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String message) {
        super(message);
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex) {
        return ex.getMessage();
    }
}
