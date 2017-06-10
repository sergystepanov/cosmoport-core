package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.LocaleDto;
import com.cosmoport.core.dto.TranslationDto;
import com.cosmoport.core.dto.TranslationLightDto;
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
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.cosmoport.core.persistence.PersistenceService.throwConstrainViolation;

/**
 * Custom database translation entities service.
 *
 * @since 0.1.0
 */
public final class TranslationPersistenceService implements HasClosableResources {
    private static Logger logger = LoggerFactory.getLogger(TranslationPersistenceService.class.getCanonicalName());
    private final Provider<DataSource> ds;
    private final I18nPersistenceService i18nPersistenceService;
    private final LocalePersistenceService localePersistenceService;

    @Inject
    public TranslationPersistenceService(Provider<DataSource> ds,
                                         I18nPersistenceService i18nPersistenceService,
                                         LocalePersistenceService localePersistenceService) {
        this.ds = ds;
        this.i18nPersistenceService = i18nPersistenceService;
        this.localePersistenceService = localePersistenceService;
    }

    /**
     * Returns all translation data for specified locale id.
     *
     * @param localeId The locale id number.
     * @since 0.1.0
     */
    public List<TranslationDto> getAllByLocaleId(final long localeId) {
        final List<TranslationDto> translations = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conn = ds.get().getConnection();

            final String sql =
                    "SELECT t.*, i.id i_id, i.tag, i.external, i.description, i.params FROM TRANSLATION t " +
                            "LEFT JOIN I18N i ON t.i18n_id = i.id WHERE t.locale_id = ?";
            statement = conn.prepareStatement(sql);
            statement.setLong(1, localeId);
            rs = statement.executeQuery();

            while (rs.next()) {
                translations.add(
                        new TranslationDto(
                                rs.getLong("id"),
                                rs.getLong("i18n_id"),
                                rs.getLong("locale_id"),
                                rs.getString("tr_text"),
                                i18nPersistenceService.mapObject(rs, "i_id")
                        )
                );
            }
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException();
        } finally {
            close(rs, statement, conn);
        }

        return translations;
    }

    Optional<TranslationDto> getById(final long id) {
        Optional<TranslationDto> object = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = ds.get().getConnection();

            final String sql = "SELECT * FROM TRANSLATION WHERE id = ?";
            statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                object = Optional.of(new TranslationDto(
                        rs.getLong("id"),
                        rs.getLong("i18n_id"),
                        rs.getLong("locale_id"),
                        rs.getString("tr_text"),
                        null)
                );
            } else {
                object = Optional.empty();
            }
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(rs, statement, conn);
        }

        return object;
    }

    /**
     * Returns all translation data in a tree structure.
     *
     * @since 0.1.0
     */
    public Map<String, Map<String, TranslationLightDto>> getAll() {
        final Map<String, Map<String, TranslationLightDto>> map = new LinkedHashMap<>();

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
                final TranslationLightDto translation = new TranslationLightDto(record.gettId(), getValues(record));
                final String locale = record.getlCode();
                final String translationKey = getKey(record);

                // Locale is first
                if (map.containsKey(locale)) {
                    map.get(locale).put(translationKey, translation);
                } else {
                    final Map<String, TranslationLightDto> tr = new HashMap<>();
                    tr.put(translationKey, translation);
                    map.put(locale, tr);
                }
            }
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(rs, statement, conn);
        }

        return map;
    }

    TranslationDto save(TranslationDto translation, final Connection extConn) throws RuntimeException {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = extConn != null ? extConn : ds.get().getConnection();

            statement = conn.prepareStatement(
                    "INSERT INTO TRANSLATION (i18n_id, locale_id, tr_text) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, translation.getI18nId());
            statement.setLong(2, translation.getLocaleId());
            statement.setString(3, translation.getText());

            if (statement.executeUpdate() < 0) {
                throw new Exception();
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    translation.setId(generatedKeys.getLong(1));
                } else {
                    throw new Exception();
                }
            }
        } catch (SQLException sqlexception) {
            throwConstrainViolation(sqlexception);
            throwServerApiException(sqlexception);
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(statement);
            if (extConn == null) {
                close(conn);
            }
        }

        return translation;
    }

    void copyOf(final TranslationDto translation, Connection extConn) throws RuntimeException {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = extConn != null ? extConn : ds.get().getConnection();

            List<LocaleDto> locales = localePersistenceService.getAll()
                    .stream()
                    .filter(locale -> locale.getId() != translation.getLocaleId())
                    .collect(Collectors.toList());

            for (final LocaleDto loc : locales) {
                statement = conn.prepareStatement(
                        "INSERT INTO TRANSLATION (i18n_id, locale_id, tr_text) VALUES (?, ?, ?)");
                statement.setLong(1, translation.getI18nId());
                statement.setLong(2, loc.getId());
                statement.setString(3, translation.getText());

                if (statement.executeUpdate() < 0) {
                    throw new Exception();
                }
            }
        } catch (SQLException sqlexception) {
            throwConstrainViolation(sqlexception);
            throwServerApiException(sqlexception);
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(statement);
            if (extConn == null) {
                close(conn);
            }
        }
    }

    /**
     * Finds a record by its i18n id.
     *
     * @param i18nId An id to find with.
     * @return Optional {@code TranslationDto} record.
     */
    List<TranslationDto> findAllByI18n(final long i18nId) {
        List<TranslationDto> objects = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = ds.get().getConnection();

            statement = conn.prepareStatement("SELECT * FROM TRANSLATION WHERE i18n_id = ?");
            statement.setLong(1, i18nId);
            rs = statement.executeQuery();

            while (rs.next()) {
                objects.add(new TranslationDto(
                        rs.getLong("id"),
                        rs.getLong("i18n_id"),
                        rs.getLong("locale_id"),
                        rs.getString("tr_text"),
                        null));
            }
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(rs, statement, conn);
        }

        return objects;
    }

    private void throwServerApiException(Throwable t) {
        logger.error(t.getMessage());
        throw new RuntimeException();
    }

    public boolean updateTranslationForId(final long id, final String text) {
        boolean result;

        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = ds.get().getConnection();

            statement = conn.prepareStatement("UPDATE TRANSLATION SET tr_text = ? WHERE id = ?");
            statement.setString(1, text);
            statement.setLong(2, id);

            result = statement.executeUpdate() == 1;
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException();
        } finally {
            close(statement, conn);
        }

        return result;
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
