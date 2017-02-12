package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.TranslationDto;
import com.cosmoport.core.persistence.constant.Constants;
import com.cosmoport.core.persistence.exception.JsonConvertException;
import com.cosmoport.core.persistence.trait.HasClosableResources;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

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

    /**
     *
     * @return
     */
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
                    "LEFT JOIN LOCALE l ON l.id = t.locale_id");

            final List<TranslationRecord> records = new ArrayList<>();
            while (rs.next()) {
                records.add(new TranslationRecord(rs.getLong("t_id"), rs.getString("t_text"), rs.getLong("i18n_id"),
                        rs.getString("i18n_tag"), rs.getBoolean("i18n_external"), rs.getString("i18n_params"),
                        rs.getString("l_code")));
            }

            // Build uber object
            for (final TranslationRecord record : records) {
                final TranslationDto translation = new TranslationDto(record.gettId(), getValues(record));
                final String locale = record.getlCode();
                final String translationKey = getKey(record);

                // Locale is first
                if (map.containsKey(locale)) {
                    map.get(locale).put(translationKey, translation);
                } else {
                    final Map<String, TranslationDto> tr = new HashMap<>();
                    tr.put(translationKey, translation);
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

    /**
     * Picks a key for a next map value.
     * <p>
     * Essentially, if a current translation record has the attribute set to true
     * then the key value should be its token value or translation value's id otherwise.
     * </p>
     *
     * @param record A translation record {@code TranslationRecord} to process.
     * @return The string value of the key.
     * @since 0.1.0
     */
    private String getKey(final TranslationRecord record) {
        return record.isI18nExternal() ? record.getI18nTag() : String.valueOf(record.getI18nId());
    }

    /**
     * Converts special cases of translation values.
     * <p>
     * In case of json array value it should convert it in a string list.
     * </p>
     *
     * @param record A translation record {@code TranslationRecord} to process.
     * @return A list of values.
     * @since 0.1.0
     */
    private List<String> getValues(final TranslationRecord record) throws JsonConvertException {
        List<String> values;

        if (record.getI18nParams().equals(Constants.jsonArray)) {
            try {
                values = Arrays.asList(new ObjectMapper().readValue(record.gettText(), String[].class));
            } catch (Exception e) {
                logger.error("Could not convert a JSON value [{}], {}", record.gettText(), e.getMessage());
                throw new JsonConvertException();
            }
        } else {
            values = ImmutableList.of(record.gettText());
        }

        return values;
    }

    private final class TranslationRecord {
        private final long tId;
        private final String tText;
        private final long i18nId;
        private final String i18nTag;
        private final boolean i18nExternal;
        private final String i18nParams;
        private final String lCode;

        private TranslationRecord(long tId, String tText, long i18nId, String i18nTag, boolean i18nExternal,
                                  String i18nParams, String lCode) {
            this.tId = tId;
            this.tText = tText;
            this.i18nId = i18nId;
            this.i18nTag = i18nTag;
            this.i18nExternal = i18nExternal;
            this.i18nParams = i18nParams;
            this.lCode = lCode;
        }

        long gettId() {
            return tId;
        }

        String gettText() {
            return tText;
        }

        long getI18nId() {
            return i18nId;
        }

        String getI18nTag() {
            return i18nTag;
        }

        boolean isI18nExternal() {
            return i18nExternal;
        }

        String getI18nParams() {
            return i18nParams;
        }

        String getlCode() {
            return lCode;
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
