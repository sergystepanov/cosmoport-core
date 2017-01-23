package com.cosmoport.core.persistence.exception;

public class PersistenceServiceException extends Exception {
    public PersistenceServiceException(String message, Exception cause) {
        super(message, cause);
    }
}
