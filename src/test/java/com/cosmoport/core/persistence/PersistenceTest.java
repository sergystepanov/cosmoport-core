package com.cosmoport.core.persistence;

import com.cosmoport.core.db.test.DatasourceServiceTestParams;
import com.cosmoport.core.db.test.DatasourceServiceTestProvider;
import com.google.inject.Provider;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class PersistenceTest {
    private static final Logger logger = LoggerFactory.getLogger(PersistenceTest.class);

    private final Provider<DataSource> dataSource;
    private Flyway flyway;

    public PersistenceTest() {
        dataSource = new DatasourceServiceTestProvider();

        flyway = new Flyway();

        // We init flyway this way because of some SQLite / Flyway incompatibilities
        Properties p = new Properties();
        p.setProperty("flyway.url", DatasourceServiceTestParams.memUrl);
        p.setProperty("flyway.user", "");
        p.setProperty("flyway.password", "");
        p.setProperty("flyway.locations", "filesystem:" + System.getProperty("user.dir") + "/db/migration");
        p.setProperty("flyway.baselineOnMigrate", "true");

        flyway.configure(p);
    }

    @BeforeEach
    public void before() {
        flyway.clean();
        flyway.migrate();
    }

    protected Provider<DataSource> getDataSource() {
        return dataSource;
    }

    protected Logger getLogger() {
        return logger;
    }
}
