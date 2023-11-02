package com.cosmoport.core.sync;

import com.cosmoport.core.dto.EventDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

record RemoteSyncObj(Types type, EventDto event) {
    @JsonCreator
    RemoteSyncObj(@JsonProperty("type") Types type, @JsonProperty("event") EventDto event) {
        this.type = type;
        this.event = event;
    }
}
