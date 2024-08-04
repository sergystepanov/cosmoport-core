package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class EventTypeCategoryDto extends Entity {
    private final long i18nEventTypeCategoryName;
    private final long parent;

    public EventTypeCategoryDto(long id,
                                @JsonAlias("i18n_event_type_category_name") long i18nEventTypeCategoryName,
                                long parent) {
        this.id = id;
        this.i18nEventTypeCategoryName = i18nEventTypeCategoryName;
        this.parent = parent;
    }

    public long getI18nEventTypeCategoryName() {
        return i18nEventTypeCategoryName;
    }

    public long getParent() {
        return parent;
    }
}
