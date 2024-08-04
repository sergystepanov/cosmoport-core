package com.cosmoport.core.config;

import com.cosmoport.core.api.error.ApiError;
import com.cosmoport.core.dto.ApiErrorDto;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.cosmoport.core.persistence.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        ApiErrorDto error = new ApiErrorDto("e-1", e.getMessage());

        int status = 500;

        if (e instanceof ApiError) {
            error = ((ApiError) e).getError();
            status = ((ApiError) e).getHttpStatus();
        }

        if (e instanceof UniqueConstraintException) {
            error = new ApiErrorDto("e-3", ((UniqueConstraintException) e).getFieldName());
            status = 400;
        }

        if (e instanceof ValidationException) {
            error = new ApiErrorDto("e-4", "Validation, " + e.getMessage());
            status = 400;
        }

        return ResponseEntity.
                status(status).
                body(error);
    }
}
