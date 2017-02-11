package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class EventTypeDto extends Entity implements Serializable {
    private static final long serialVersionUID = 3477232786787312639L;

    private long i18nEventTypeName;
    private long i18nEventTypeSubname;
    private String eventTypeDescription;
    private int defaultDuration;
    private int defaultRepeatInterval;

    @JsonCreator
    public EventTypeDto(@JsonProperty("id") long id,
                        @JsonProperty("i18n_event_type_name") long i18nEventTypeName,
                        @JsonProperty("i18n_event_type_subname") long i18nEventTypeSubname,
                        @JsonProperty("event_type_description") String eventTypeDescription,
                        @JsonProperty("default_duration") int defaultDuration,
                        @JsonProperty("default_repeat_interval") int defaultRepeatInterval) {
        this.id = id;
        this.i18nEventTypeName = i18nEventTypeName;
        this.i18nEventTypeSubname = i18nEventTypeSubname;
        this.eventTypeDescription = eventTypeDescription;
        this.defaultDuration = defaultDuration;
        this.defaultRepeatInterval = defaultRepeatInterval;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getI18nEventTypeName() {
        return i18nEventTypeName;
    }

    public long getI18nEventTypeSubname() {
        return i18nEventTypeSubname;
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
