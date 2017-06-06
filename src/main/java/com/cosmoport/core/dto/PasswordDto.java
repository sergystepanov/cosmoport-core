package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class PasswordDto implements Serializable {
    private static final long serialVersionUID = 1589220062134285057L;
    private final String pwd;

    @JsonCreator
    public PasswordDto(@JsonProperty("pwd") String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }
}
