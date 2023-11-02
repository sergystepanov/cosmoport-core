package com.cosmoport.core.persistence.exception;

import java.io.Serial;

public class UniqueConstraintException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5617963591633753213L;

    private final String fieldName;

    public UniqueConstraintException(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
