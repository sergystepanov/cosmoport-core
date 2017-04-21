package com.cosmoport.core.db.test;

import com.cosmoport.core.db.DatasourceService;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

public class HikariSQLiteTestDatasourceServiceImpl implements DatasourceService {
    private DataSource ds;

    HikariSQLiteTestDatasourceServiceImpl() {
        ds = new SingleConnectionDataSource(DatasourceServiceTestParams.getRandomMemUrl(System.nanoTime()), true);
    }

    @Override
    public DataSource getSource() {
//        if (ds == null) {
            //return new SingleConnectionDataSource(DatasourceServiceTestParams.getRandomMemUrl(System.nanoTime()), true);
//        }

//        return ds;
        return ds;
    }
}
