package com.epam.test.client.exception;

/**
 * Created by charadziej on 2/27/17.
 */

public class ServerDataAccessException extends RuntimeException {

    public ServerDataAccessException(String message) {
        super(message);
    }

    public ServerDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
