package com.cosmoport.core.persistence;

import com.cosmoport.core.db.test.DatasourceServiceTestProvider;
import com.google.inject.Provider;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * Base class for persistence testing.
 *
 * @since 0.1.0
 */
public class PersistenceTest {
    private static final Logger logger = LoggerFactory.getLogger(PersistenceTest.class);
    private Provider<DataSource> dataSourceProvider;
    private final Flyway flyway;

    public PersistenceTest() {
        flyway = new Flyway();
        flyway.setLocations("filesystem:" + System.getProperty("user.dir") + "/db/migration");
        flyway.setBaselineOnMigrate(true);
    }

    @BeforeEach
    public void before() {
        // Create new in-memory database before each test
        dataSourceProvider = new DatasourceServiceTestProvider();
        // Apply all migrations
        flyway.setDataSource(dataSourceProvider.get());
        flyway.migrate();
    }

    public Provider<DataSource> getDataSourceProvider() {
        return dataSourceProvider;
    }

    public Logger getLogger() {
        return logger;
    }
}
