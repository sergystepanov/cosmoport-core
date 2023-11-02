package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PasswordDto(String pwd) {
    @JsonCreator
    public PasswordDto(@JsonProperty("pwd") String pwd) {
        this.pwd = pwd;
    }
}
