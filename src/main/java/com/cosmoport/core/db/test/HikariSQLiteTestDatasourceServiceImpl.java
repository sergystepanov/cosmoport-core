package com.cosmoport.core.db.test;

import com.cosmoport.core.db.DatasourceService;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

public class HikariSQLiteTestDatasourceServiceImpl implements DatasourceService {
    private static DataSource ds;

    @Override
    public DataSource getSource() {
        if (ds == null) {
            SingleConnectionDataSource dss = new SingleConnectionDataSource(DatasourceServiceTestParams.memUrl, true);
//            HikariConfig config = new HikariConfig();
//            config.setDataSourceClassName("org.sqlite.SQLiteDataSource");
//            config.addDataSourceProperty("url", DatasourceServiceTestParams.memUrl);

//            ds = new HikariDataSource(config);
            ds = dss;
        }

        return ds;
    }
}
