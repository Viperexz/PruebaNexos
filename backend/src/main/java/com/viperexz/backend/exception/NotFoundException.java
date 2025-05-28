package com.viperexz.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String message) {
        super(message);
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(NotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error/not-found");
        modelAndView.setStatus(HttpStatus.NO_CONTENT);
        return modelAndView;
    }
}
