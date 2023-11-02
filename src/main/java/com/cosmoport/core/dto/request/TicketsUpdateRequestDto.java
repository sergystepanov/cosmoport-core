package com.cosmoport.core.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TicketsUpdateRequestDto(long id, int tickets, boolean forceOpen){
    @JsonCreator
    public TicketsUpdateRequestDto(@JsonProperty("id") long id,
                                   @JsonProperty("tickets") int tickets,
                                   @JsonProperty("force_open") boolean forceOpen) {
        this.id = id;
        this.tickets = tickets;
        this.forceOpen = forceOpen;
    }
}
