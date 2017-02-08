package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class EventTypeDto extends Entity implements Serializable {
    private static final long serialVersionUID = 3477232786787312639L;

    private String eventTypeName;
    private String eventTypeSubname;
    private String eventTypeDescription;
    private int defaultDuration;
    private int defaultRepeatInterval;

    @JsonCreator
    public EventTypeDto(@JsonProperty("id") long id,
                        @JsonProperty("event_type_name") String eventTypeName,
                        @JsonProperty("event_type_subname") String eventTypeSubname,
                        @JsonProperty("event_type_description") String eventTypeDescription,
                        @JsonProperty("default_duration") int defaultDuration,
                        @JsonProperty("default_repeat_interval") int defaultRepeatInterval) {
        this.id = id;
        this.eventTypeName = eventTypeName;
        this.eventTypeSubname = eventTypeSubname;
        this.eventTypeDescription = eventTypeDescription;
        this.defaultDuration = defaultDuration;
        this.defaultRepeatInterval = defaultRepeatInterval;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public String getEventTypeSubname() {
        return eventTypeSubname;
    }

    public String getEventTypeDescription() {
        return eventTypeDescription;
    }

    public int getDefaultDuration() {
        return defaultDuration;
    }

    public int getDefaultRepeatInterval() {
        return defaultRepeatInterval;
    }
}
