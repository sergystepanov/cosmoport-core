package com.cosmoport.core.api.error;

import com.cosmoport.core.dto.ApiErrorDto;

public class ApiAuthError extends Exception implements ApiError {
    private static final long serialVersionUID = -2149236424230204297L;

    @Override
    public ApiErrorDto getError() {
        return new ApiErrorDto("e-5", "Unauthorized access.");
    }

    @Override
    public int getHttpStatus() {
        return 401;
    }
}
