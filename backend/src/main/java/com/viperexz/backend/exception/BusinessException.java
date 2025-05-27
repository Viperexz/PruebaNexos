package com.viperexz.backend.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    @ExceptionHandler(BusinessException.class)
    public String handleBussinessException(BusinessException ex) {
        return ex.getMessage();
    }
}
