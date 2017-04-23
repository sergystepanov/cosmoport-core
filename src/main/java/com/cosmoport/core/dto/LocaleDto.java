package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Database LOCALE table entity.
 *
 * @since 0.1.0
 */
public final class LocaleDto extends Entity implements Serializable {
    private static final long serialVersionUID = 1200118022748392404L;

    private final String code;
    private final boolean isDefault;
    private final String localeDescription;
    private final boolean show;
    private final int showTime;

    @JsonCreator
    public LocaleDto(@JsonProperty("id") long id,
                     @JsonProperty("code") String code,
                     @JsonProperty("is_default") boolean isDefault,
                     @JsonProperty("locale_description") String localeDescription,
                     @JsonProperty("show") boolean show,
                     @JsonProperty("show_time") int showTime) {
        this.show = show;
        this.showTime = showTime;
        this.id = id;
        this.code = code;
        this.isDefault = isDefault;
        this.localeDescription = localeDescription;
    }

    public String getCode() {
        return code;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getLocaleDescription() {
        return localeDescription;
    }

    public boolean isShow() {
        return show;
    }

    public int getShowTime() {
        return showTime;
    }
}
