package com.github.charadziej.project.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody String handleDataAccessException(DataAccessException ex) {
        LOGGER.debug("Handling DataAccessException: " + ex);
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody String handleIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.debug("Handling IllegalArgumentException: " + ex.getLocalizedMessage());
        return ex.getLocalizedMessage();
    }
}
