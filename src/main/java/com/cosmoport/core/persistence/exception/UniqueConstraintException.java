package com.cosmoport.core.persistence.exception;

public class UniqueConstraintException extends Exception {
    private final String fieldName;

    public UniqueConstraintException(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
