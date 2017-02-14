package com.cosmoport.core.dto;

import java.io.Serializable;

/**
 * Request result object.
 *
 * @since 0.1.0
 */
public final class ResultDto implements Serializable {
    private static final long serialVersionUID = -3128509528152039308L;

    private final boolean result;

    public ResultDto(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
