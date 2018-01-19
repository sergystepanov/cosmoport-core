package com.cosmoport.core.db.test;

public final class DatasourceServiceTestParams {
    public static final String memUrl = "jdbc:sqlite:file:testdb?mode=memory&cache=shared";
    static String getRandomMemUrl(long time) {
        return "jdbc:sqlite:file:testdb" + time + "?mode=memory&cache=shared";
    }
}
