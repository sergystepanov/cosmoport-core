package com.cosmoport.core.dto;

import java.io.Serializable;

public final class TranslationDto extends Entity implements Serializable {
    private static final long serialVersionUID = 6700819328646792540L;

    private final long i18nId;
    private final long localeId;
    private final String text;
    private final I18nDto i18n;

    public TranslationDto(long id, long i18nId, long localeId, String text, I18nDto i18n) {
        this.id = id;
        this.i18nId = i18nId;
        this.localeId = localeId;
        this.text = text;
        this.i18n = i18n;
    }

    public long getI18nId() {
        return i18nId;
    }

    public long getLocaleId() {
        return localeId;
    }

    public String getText() {
        return text;
    }

    public I18nDto getI18n() {
        return i18n;
    }

    @Override
    public String toString() {
        return "TranslationDto{" + "i18nId=" + i18nId + ", localeId=" + localeId + ", text='" + text + '\'' +
                ", i18n=" + i18n + '}';
    }
}
