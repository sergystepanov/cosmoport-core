package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class GateDto extends Entity implements Serializable {
    private static final long serialVersionUID = 6418537348575327742L;

    private final int number;
    private final String gateName;

    @JsonCreator
    public GateDto(@JsonProperty("number") int number, @JsonProperty("gate_name") String gateName) {
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
