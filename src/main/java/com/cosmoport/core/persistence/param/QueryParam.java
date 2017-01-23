package com.cosmoport.core.persistence.param;

public final class QueryParam {
    private final String type;
    private final Object value;

    public QueryParam(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
