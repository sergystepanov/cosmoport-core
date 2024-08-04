package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Database LOCALE table entity.
 *
 * @since 0.1.0
 */
public final class LocaleDto extends Entity {
    private final String code;
    private final boolean isDefault;
    private final String localeDescription;
    private final boolean show;
    private final int showTime;

    public LocaleDto(long id,
                     String code,
                     @JsonAlias("is_default") boolean isDefault,
                     @JsonAlias("locale_description") String localeDescription,
                     boolean show,
                     @JsonAlias("show_time") int showTime) {
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
