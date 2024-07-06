package com.transaction.demo.exception;

/**
 * Custom checked exception
 */
public class ApplicationException extends Exception {

    public ApplicationException(String msg) {
        super(msg);
    }
}
