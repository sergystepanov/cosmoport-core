package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class EventTypeDto extends Entity {
    private final long i18nEventTypeName;
    private final long i18nEventTypeSubname;
    private final long i18nEventTypeDescription;
    private final int defaultDuration;
    private final int defaultRepeatInterval;

    @JsonCreator
    public EventTypeDto(@JsonProperty("id") long id,
                        @JsonProperty("i18n_event_type_name") long i18nEventTypeName,
                        @JsonProperty("i18n_event_type_subname") long i18nEventTypeSubname,
                        @JsonProperty("i18n_event_type_description") long i18nEventTypeDescription,
                        @JsonProperty("default_duration") int defaultDuration,
                        @JsonProperty("default_repeat_interval") int defaultRepeatInterval) {
        this.id = id;
        this.i18nEventTypeName = i18nEventTypeName;
        this.i18nEventTypeSubname = i18nEventTypeSubname;
        this.i18nEventTypeDescription = i18nEventTypeDescription;
        this.defaultDuration = defaultDuration;
        this.defaultRepeatInterval = defaultRepeatInterval;
    }

    public long getI18nEventTypeName() {
        return i18nEventTypeName;
    }

    public long getI18nEventTypeSubname() {
        return i18nEventTypeSubname;
    }

    public long getI18nEventTypeDescription() {
        return i18nEventTypeDescription;
    }

    public int getDefaultDuration() {
        return defaultDuration;
    }

    public int getDefaultRepeatInterval() {
        return defaultRepeatInterval;
    }
}
