package com.cosmoport.core.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class CreateEventTypeRequestDto implements Serializable {
    private static final long serialVersionUID = -6844418056508971397L;

    private final String name;
    private final String subname;
    private final String description;
    private final int defaultDuration;
    private final int defaultRepeatInterval;

    @JsonCreator
    public CreateEventTypeRequestDto(@JsonProperty("name") String name,
                                     @JsonProperty("subname") String subname,
                                     @JsonProperty("description") String description,
                                     @JsonProperty("default_duration") int defaultDuration,
                                     @JsonProperty("default_repeat_interval") int defaultRepeatInterval) {
        this.name = name;
        this.subname = subname;
        this.description = description;
        this.defaultDuration = defaultDuration;
        this.defaultRepeatInterval = defaultRepeatInterval;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public String getSubname() {
        return subname;
    }

    public String getDescription() {
        return description;
    }

    public int getDefaultDuration() {
        return defaultDuration;
    }

    public int getDefaultRepeatInterval() {
        return defaultRepeatInterval;
    }
}
