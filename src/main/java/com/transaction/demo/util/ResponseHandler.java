package com.transaction.demo.util;

import com.transaction.demo.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

/**
 * Response handler utility
 */
public final class ResponseHandler {

    private ResponseHandler() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Generates http response with given parameters
     *
     * @param httpStatus http status
     * @param message response message
     * @param data response data
     * @param <T> generic class
     * @return ResponseEntity
     */
    public static <T> ResponseEntity<Response<T>> generateResponse(HttpStatus httpStatus, String message, T data) {
        Response<T> apiResponse = new Response<>();
        apiResponse.setTimeStamp(new Date().getTime());
        apiResponse.setHttpStatus(httpStatus);
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    /**
     * Generates status code 200 (OK) HTTP response with given parameters
     *
     * @param message response message
     * @param data response data
     * @param <T> generic class
     * @return ResponseEntity
     */
    public static <T> ResponseEntity<Response<T>> generateSuccess(String message, T data) {
        return generateResponse(HttpStatus.OK, message, data);
    }

    /**
     * Generates status code 400 (bad request) HTTP response with given parameters
     *
     * @param message response message
     * @param data response data
     * @param <T> generic class
     * @return ResponseEntity
     */
    public static <T> ResponseEntity<Response<T>> generateError(String message, T data) {
        return generateResponse(HttpStatus.BAD_REQUEST, message, data);
    }

}
