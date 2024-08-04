package com.cosmoport.core.dto;

public record ProxyRequestDto(String name, EventDto event, String type) {
    public ProxyRequestDto(String name, EventDto event, String type) {
        this.name = name;
        this.event = event;
        this.type = type;
    }
}
