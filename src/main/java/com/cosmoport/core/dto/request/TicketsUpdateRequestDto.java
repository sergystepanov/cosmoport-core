package com.cosmoport.core.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TicketsUpdateRequestDto implements Serializable {
    private static final long serialVersionUID = -8100932634952174327L;

    private final long id;
    private final int tickets;
    private final boolean forceOpen;

    @JsonCreator
    public TicketsUpdateRequestDto(@JsonProperty("id") long id,
                                   @JsonProperty("tickets") int tickets,
                                   @JsonProperty("force_open") boolean forceOpen) {
        this.id = id;
        this.tickets = tickets;
        this.forceOpen = forceOpen;
    }

    public long getId() {
        return id;
    }

    public int getTickets() {
        return tickets;
    }

    public boolean isForceOpen() {
        return forceOpen;
    }
}
