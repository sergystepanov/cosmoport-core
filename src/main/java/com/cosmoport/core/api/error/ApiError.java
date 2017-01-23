package com.cosmoport.core.api.error;

import com.cosmoport.core.dto.ApiErrorDto;

public interface ApiError {
    ApiErrorDto getError();

    int getHttpStatus();
}
