package com.cosmoport.core.persistence.exception;

public final class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 2909626414579344513L;

    public ValidationException(final String message) {
        super(message);
    }
}
