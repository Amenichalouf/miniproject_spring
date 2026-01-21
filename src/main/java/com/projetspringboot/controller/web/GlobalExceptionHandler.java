package com.projetspringboot.controller.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        // Log the error (optional)
        e.printStackTrace();

        model.addAttribute("error", "Une erreur inattendue est survenue.");
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFound(NoResourceFoundException e, Model model) {
        model.addAttribute("error", "Page non trouvée.");
        model.addAttribute("message", "La ressource demandée n'existe pas.");
        return "error";
    }
}
