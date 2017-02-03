package com.cosmoport.core.db.test;

import com.cosmoport.core.db.DatasourceService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class HikariSQLiteTestDatasourceServiceImpl implements DatasourceService {
    private static DataSource ds;

    @Override
    public DataSource getSource() {
        if (ds == null) {
            HikariConfig config = new HikariConfig();
            config.setDataSourceClassName("org.sqlite.SQLiteDataSource");
            config.addDataSourceProperty("url", DatasourceServiceTestParams.memUrl);

            ds = new HikariDataSource(config);
        }

        return ds;
    }
}
