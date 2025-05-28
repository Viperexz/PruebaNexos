package com.viperexz.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;


public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(BusinessException ex, Model model, WebRequest request) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("requestDescription", request.getDescription(false)); // Información adicional de la solicitud
        ModelAndView modelAndView = new ModelAndView("error/business-error");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }


}
