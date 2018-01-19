package com.cosmoport.core.sync;

import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.dto.SyncServerParamsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public final class RemoteSync {
    private final SyncServerParamsDto settings;
    private static final Logger logger = LoggerFactory.getLogger(RemoteSync.class);

    public RemoteSync(SyncServerParamsDto settings) {
        this.settings = settings;
    }

    public void process(Types type, EventDto event) {
        if (settings.getSyncServerOn().equals("on")) {
            final RemoteSyncObj object = new RemoteSyncObj(type, event);
            // Convert to JSON
            ObjectMapper mapper = new ObjectMapper();
            OutputStreamWriter out = null;
            try {
                String value = mapper.writeValueAsString(object);

                // Request
                URL url = new URL(settings.getSyncServerAddress());
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setRequestMethod("POST");
                out = new OutputStreamWriter(httpCon.getOutputStream());
                out.write(value);
                logger.info("[sync] [out] {}, {}", httpCon.getResponseCode(), httpCon.getResponseMessage());
                out.close();
            } catch (Exception e) {
                logger.error("[sync] [out] {}", e.getMessage());
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        logger.error("[sync] [out] {}", e.getMessage());
                    }
                }
            }
        } else {
            logger.info("[sync] [out] disabled");
        }
    }
}
