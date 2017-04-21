package com.cosmoport.core.db.test;

import com.google.inject.Provider;

import javax.sql.DataSource;

public class DatasourceServiceTestProvider implements Provider<DataSource> {
    private DataSource ds;

    @Override
    public DataSource get() {
        if (ds == null) {
            ds = new HikariSQLiteTestDatasourceServiceImpl().getSource();
        }

        return ds;
    }
}
