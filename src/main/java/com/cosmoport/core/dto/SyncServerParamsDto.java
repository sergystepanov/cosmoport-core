package com.cosmoport.core.dto;

public class SyncServerParamsDto {
    private final String syncServerAddress;
    private final String syncServerKey;
    private final String syncServerOn;

    SyncServerParamsDto(String syncServerAddress, String syncServerKey, String syncServerOn) {
        this.syncServerAddress = syncServerAddress;
        this.syncServerKey = syncServerKey;
        this.syncServerOn = syncServerOn;
    }

    public String getSyncServerAddress() {
        return syncServerAddress;
    }

    public String getSyncServerKey() {
        return syncServerKey;
    }

    public String getSyncServerOn() {
        return syncServerOn;
    }

}
