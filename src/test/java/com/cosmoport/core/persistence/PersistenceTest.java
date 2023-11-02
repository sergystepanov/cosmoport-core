package com.cosmoport.core.persistence;

import com.cosmoport.core.db.test.DatasourceServiceTestProvider;
import com.google.inject.Provider;
import org.flywaydb.core.Flyway;
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
    private final Provider<DataSource> dataSourceProvider;
    private final Flyway flyway;

    public PersistenceTest() {
        dataSourceProvider = new DatasourceServiceTestProvider();
        flyway = Flyway.configure()
                .locations("filesystem:" + System.getProperty("user.dir") + "/db/migration")
                // Create new in-memory database before each test
                .dataSource(dataSourceProvider.get())
                .baselineOnMigrate(true)
                .mixed(true)
                .load();
    }


    public void before() {
        flyway.clean();
        flyway.migrate();
    }

    protected Provider<DataSource> getDataSourceProvider() {
        return dataSourceProvider;
    }

    protected Logger getLogger() {
        return logger;
    }
}
