package com.cosmoport.core.dto;

public final class SettingsDto extends Entity {
    private final String param;
    private final String value;

    public SettingsDto(final long id, String param, String value) {
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
