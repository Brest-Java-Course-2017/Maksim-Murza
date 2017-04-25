package com.github.charadziej.project.client.rest;

import com.github.charadziej.project.client.ServerDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.util.List;

/**
 * Data Access Response Error Handler.
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return errorHandler.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        /*HttpHeaders headers = response.getHeaders();*/
        throw new ServerDataAccessException(response.getStatusCode() + ": " + response.getStatusText());
    }
}
