package com.cosmoport.core.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;

public record TicketsUpdateRequestDto(long id, int tickets, boolean forceOpen) {
    public TicketsUpdateRequestDto(long id,
                                   int tickets,
                                   @JsonAlias("force_open") boolean forceOpen) {
        this.id = id;
        this.tickets = tickets;
        this.forceOpen = forceOpen;
    }
}
