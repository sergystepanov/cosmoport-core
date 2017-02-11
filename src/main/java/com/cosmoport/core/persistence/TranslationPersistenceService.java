package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.TranslationDto;
import com.cosmoport.core.persistence.trait.HasClosableResources;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Custom database translation entities service.
 *
 * @since 0.1.0
 */
public final class TranslationPersistenceService implements HasClosableResources {
    private static Logger logger = LoggerFactory.getLogger(TranslationPersistenceService.class.getCanonicalName());
    private final Provider<DataSource> ds;

    @Inject
    public TranslationPersistenceService(Provider<DataSource> ds) {
        this.ds = ds;
    }

    public Map<String, Map<String, TranslationDto>> getAll() {
        final Map<String, Map<String, TranslationDto>> map = new HashMap<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            conn = ds.get().getConnection();

            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT t.id t_id, tr_text t_text, " +
                    "  t.i18n_id, i18n.tag i18n_tag, i18n.external i18n_external, i18n.description i18n_description, " +
                    "  i18n.params i18n_params, " +
                    "  t.locale_id, l.code l_code " +
                    "FROM TRANSLATION t " +
                    "LEFT JOIN I18N i18n ON i18n.id = t.i18n_id " +
                    "LEFT JOIN LOCALE l on l.id = t.locale_id");

            final List<TranslationRecord> records = new ArrayList<>();
            while (rs.next()) {
                //objects.add(map(rs));
                records.add(new TranslationRecord(rs.getLong("t_id"), rs.getString("t_text"), rs.getLong("i18n_id"),
                        rs.getString("i18n_tag"), rs.getBoolean("i18n_external"),
                        rs.getString("i18n_description"), rs.getString("i18n_params"),
                        rs.getLong("locale_id"), rs.getString("l_code")));
            }

            // Build uber object
            for (final TranslationRecord record : records) {
                final TranslationDto translation =
                        new TranslationDto(record.gettId(), ImmutableList.of(record.gettText()));
                final String locale = record.getlCode();
                // Locale is first
                if (map.containsKey(locale)) {
                    map.get(locale).put(record.getI18nTag(), translation);
                } else  {
                    final Map<String, TranslationDto> tr = new HashMap<>();
                    tr.put(record.getI18nTag(), translation);
                    map.put(locale, tr);
                }
            }

        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException();
        } finally {
            close(rs, statement, conn);
        }

        return map;
    }

    private final class TranslationRecord {
        private final long tId;
        private final String tText;
        private final long i18nId;
        private final String i18nTag;
        private final boolean i18nExternal;
        private final String i18nDescription;
        private final String i18nParams;
        private final long localeId;
        private final String lCode;

        private TranslationRecord(long tId, String tText, long i18nId, String i18nTag, boolean i18nExternal,
                                  String i18nDescription, String i18nParams, long localeId, String lCode) {
            this.tId = tId;
            this.tText = tText;
            this.i18nId = i18nId;
            this.i18nTag = i18nTag;
            this.i18nExternal = i18nExternal;
            this.i18nDescription = i18nDescription;
            this.i18nParams = i18nParams;
            this.localeId = localeId;
            this.lCode = lCode;
        }

        public long gettId() {
            return tId;
        }

        public String gettText() {
            return tText;
        }

        public long getI18nId() {
            return i18nId;
        }

        public String getI18nTag() {
            return i18nTag;
        }

        public boolean isI18nExternal() {
            return i18nExternal;
        }

        public String getI18nDescription() {
            return i18nDescription;
        }

        public String getI18nParams() {
            return i18nParams;
        }

        public long getLocaleId() {
            return localeId;
        }

        public String getlCode() {
            return lCode;
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
