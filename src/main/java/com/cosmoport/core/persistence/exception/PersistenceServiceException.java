package com.cosmoport.core.persistence.exception;

public class PersistenceServiceException extends Exception {
    private static final long serialVersionUID = 8704716477438677964L;

    public PersistenceServiceException(String message, Exception cause) {
        super(message, cause);
    }
}
