package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class EventDestinationDto extends Entity {
    private final long i18nEventDestinationName;

    @JsonCreator
    public EventDestinationDto(@JsonProperty("id") long id,
                               @JsonProperty("i18n_event_destination_name") long i18nEventDestinationName) {
        this.id = id;
        this.i18nEventDestinationName = i18nEventDestinationName;
    }

    public long getI18nEventDestinationName() {
        return i18nEventDestinationName;
    }
}
