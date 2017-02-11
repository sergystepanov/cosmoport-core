package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class EventDestinationDto extends Entity implements Serializable {
    private static final long serialVersionUID = -1706846634689840968L;

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
