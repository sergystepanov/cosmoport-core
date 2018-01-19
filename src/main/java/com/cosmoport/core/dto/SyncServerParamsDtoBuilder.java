package com.cosmoport.core.dto;

public class SyncServerParamsDtoBuilder {
    private String syncServerAddress = "localhost";
    private String syncServerKey = "123456";
    private String syncServerOn = "off";

    public SyncServerParamsDtoBuilder setSyncServerAddress(String syncServerAddress) {
        this.syncServerAddress = syncServerAddress;
        return this;
    }

    public SyncServerParamsDtoBuilder setSyncServerKey(String syncServerKey) {
        this.syncServerKey = syncServerKey;
        return this;
    }

    public SyncServerParamsDtoBuilder setSyncServerOn(String syncServerOn) {
        this.syncServerOn = syncServerOn;
        return this;
    }

    public SyncServerParamsDto createSyncServerParamsDto() {
        return new SyncServerParamsDto(syncServerAddress, syncServerKey, syncServerOn);
    }
}