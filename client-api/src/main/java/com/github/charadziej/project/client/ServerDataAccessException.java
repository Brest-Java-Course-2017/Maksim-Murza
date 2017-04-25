package com.github.charadziej.project.client;

/**
 * Exception which is thrown on the web-server
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
