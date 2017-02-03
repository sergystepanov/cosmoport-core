package com.cosmoport.core.db.test;

import com.google.inject.Provider;

import javax.sql.DataSource;

public class DatasourceServiceTestProvider implements Provider<DataSource> {
    @Override
    public DataSource get() {
        return new HikariSQLiteTestDatasourceServiceImpl().getSource();
    }
}
