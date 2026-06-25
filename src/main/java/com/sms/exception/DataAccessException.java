package com.sms.exception;

/**
 * Unchecked exception used to wrap low-level SQLExceptions so the
 * upper layers (service, servlet) don't depend on java.sql.
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
