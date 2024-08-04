package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.io.Serial;
import java.io.Serializable;

public final class EventStatusDto extends Entity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2607534861161087817L;

    private final long i18nStatus;

    public EventStatusDto(long id, @JsonAlias("i18n_status") long i18nStatus) {
        this.id = id;
        this.i18nStatus = i18nStatus;
    }

    public long getI18nStatus() {
        return i18nStatus;
    }
}
