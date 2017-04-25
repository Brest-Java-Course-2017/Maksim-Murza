package com.github.charadziej.project.web_app.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class WebAppExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        LOGGER.debug(ex.getMessage());
        model.addAttribute("ex", ex);
        return "exception";
    }
}
