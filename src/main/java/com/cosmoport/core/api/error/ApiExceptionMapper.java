package com.cosmoport.core.api.error;

import com.cosmoport.core.dto.ApiErrorDto;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.cosmoport.core.persistence.exception.ValidationException;
import com.google.inject.Singleton;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
public class ApiExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
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

        return Response.status(status).
                entity(error).
                type(MediaType.APPLICATION_JSON).
                build();
    }
}
