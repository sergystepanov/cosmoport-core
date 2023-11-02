package com.cosmoport.core.persistence.exception;

import java.io.Serial;

public class PersistenceServiceException extends Exception {
    @Serial
    private static final long serialVersionUID = 8704716477438677964L;

    public PersistenceServiceException(String message, Exception cause) {
        super(message, cause);
    }
}
