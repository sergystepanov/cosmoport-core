package com.cosmoport.core.db;

import com.cosmoport.core.config.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

class HikariSQLiteDatasourceServiceImpl implements DatasourceService {
    private final String path;

    HikariSQLiteDatasourceServiceImpl(String path) {
        this.path = path;
    }

    @Override
    public DataSource getSource() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.sqlite.SQLiteDataSource");
        config.addDataSourceProperty("url", "jdbc:sqlite:" + path + Config.DB_NAME);

        // enableSQLiteWAL(config);
        // or just HikariDataSource(config)
        return new HikariDataSource(config);
    }

    private DataSource enableSQLiteWAL(HikariConfig config) {
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(0);
        config.setIdleTimeout(60000);
        //config.setMaxLifetime(35000);
        DataSource ds = new HikariDataSource(config);

        Connection conn = null;
        try {
            conn = ds.getConnection();
            conn.prepareStatement("PRAGMA journal_mode = WAL; PRAGMA synchronous = NORMAL;").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return ds;
    }
}
