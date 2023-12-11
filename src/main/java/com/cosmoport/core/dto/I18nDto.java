package com.cosmoport.core.dto;

public final class I18nDto extends Entity {
    private final String tag;
    private final boolean external;
    private final String description;
    private final String params;

    public I18nDto(long id, String tag, boolean external, String description, String params) {
        this.id = id;
        this.tag = tag;
        this.external = external;
        this.description = description;
        this.params = params;
    }

    public static I18nDto Short(String tag) {
        return new I18nDto(0, tag, false, "", "");
    }

    public String getTag() {
        return tag;
    }

    public boolean isExternal() {
        return external;
    }

    public String getDescription() {
        return description;
    }

    public String getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "I18nDto{" + "id=" + id +
                ", tag='" + tag + '\'' + ", external=" + external + ", description='" + description + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
