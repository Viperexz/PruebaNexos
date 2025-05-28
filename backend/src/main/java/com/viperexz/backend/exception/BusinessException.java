package com.viperexz.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(BusinessException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error/business-error");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }


}
