package com.cosmoport.core.dto;

/**
 * API error object.
 *
 * @since 0.0.1
 */
public record ApiErrorDto(String code, String message) {
    public ApiErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
