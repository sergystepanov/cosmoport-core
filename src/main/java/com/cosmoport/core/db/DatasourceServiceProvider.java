package com.cosmoport.core.db;

import com.cosmoport.core.file.FilePath;
import com.cosmoport.core.file.FileSystem;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.sql.DataSource;

public class DatasourceServiceProvider implements Provider<DataSource> {
    private final String databasePath;

    @Inject
    public DatasourceServiceProvider(FileSystem fileSystem) {
        databasePath = fileSystem.resolve(FilePath.DATABASE);
    }

    @Override
    public DataSource get() {
        return new HikariSQLiteDatasourceServiceImpl(databasePath).getSource();
    }
}
