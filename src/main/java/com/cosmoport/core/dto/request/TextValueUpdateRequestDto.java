package com.cosmoport.core.dto.request;

public record TextValueUpdateRequestDto(String text) {
    public TextValueUpdateRequestDto(String text) {
        this.text = text;
    }
}
