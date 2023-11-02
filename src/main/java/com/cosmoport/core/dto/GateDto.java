package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class GateDto extends Entity {
    private final int number;
    private final String gateName;

    @JsonCreator
    public GateDto(@JsonProperty("id") long id,
                   @JsonProperty("number") int number,
                   @JsonProperty("gate_name") String gateName) {
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
