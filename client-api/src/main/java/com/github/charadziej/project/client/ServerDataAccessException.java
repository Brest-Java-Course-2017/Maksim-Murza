package com.github.charadziej.project.client;

/**
 * Created by charadziej on 4/13/17.
 */

public class ServerDataAccessException extends RuntimeException {

    public ServerDataAccessException() {}

    public ServerDataAccessException(String message) {
        super(message);
    }

    public ServerDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
