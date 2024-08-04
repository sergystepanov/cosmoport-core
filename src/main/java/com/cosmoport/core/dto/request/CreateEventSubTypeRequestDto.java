package com.cosmoport.core.dto.request;

public record CreateEventSubTypeRequestDto(String name, String description) {
    public CreateEventSubTypeRequestDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
