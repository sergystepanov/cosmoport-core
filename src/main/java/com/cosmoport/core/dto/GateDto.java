package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public final class GateDto extends Entity {
    private final int number;
    private final String gateName;

    public GateDto(long id, int number, @JsonAlias("gate_name") String gateName) {
        this.id = id;
        this.number = number;
        this.gateName = gateName;
    }

    public int getNumber() {
        return number;
    }

    public String getGateName() {
        return gateName;
    }
}
