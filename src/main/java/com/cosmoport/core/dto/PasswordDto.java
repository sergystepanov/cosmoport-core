package com.cosmoport.core.dto;

public record PasswordDto(String pwd) {
    public PasswordDto(String pwd) {
        this.pwd = pwd;
    }
}
