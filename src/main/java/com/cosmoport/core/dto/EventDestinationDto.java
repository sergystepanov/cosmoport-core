package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public final class EventDestinationDto extends Entity {
    private final long i18nEventDestinationName;

    public EventDestinationDto(long id,
                               @JsonAlias("i18n_event_destination_name") long i18nEventDestinationName) {
        this.id = id;
        this.i18nEventDestinationName = i18nEventDestinationName;
    }

    public long getI18nEventDestinationName() {
        return i18nEventDestinationName;
    }
}
