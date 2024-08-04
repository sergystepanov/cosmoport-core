package com.cosmoport.core.dto.request;

public record CreateEventTypeCategoryRequestDto(String name) {
    public CreateEventTypeCategoryRequestDto(String name) {
        this.name = name;
    }
}
