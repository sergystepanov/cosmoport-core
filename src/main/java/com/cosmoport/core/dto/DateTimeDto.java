package com.cosmoport.core.dto;

public record DateTimeDto(long timestamp) {
    public DateTimeDto(long timestamp) {
        this.timestamp = timestamp;
    }
}
