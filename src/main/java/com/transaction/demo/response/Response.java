package com.transaction.demo.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Generic API response class
 *
 * @param <T> generic class
 */
@Getter
@Setter
public class Response<T> {
    private Long timeStamp;
    private HttpStatus httpStatus;
    private String message;
    private T data;
}
