package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class SettingsDto extends Entity {
    private final String param;
    private final String value;

    @JsonCreator
    public SettingsDto(@JsonProperty("id") final long id,
                       @JsonProperty("param") String param,
                       @JsonProperty("value") String value) {
        this.id = id;
        this.param = param;
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public String getValue() {
        return value;
    }
}
