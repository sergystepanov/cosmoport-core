package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * API error object.
 *
 * @since 0.0.1
 */
public final class ApiErrorDto implements Serializable {
    private static final long serialVersionUID = -4626928791739990148L;

    private final String code;
    private final String message;

    @JsonCreator
    public ApiErrorDto(@JsonProperty("code") String code, @JsonProperty("message") String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
