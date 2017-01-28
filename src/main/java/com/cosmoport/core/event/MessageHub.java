package com.cosmoport.core.event;

import com.cosmoport.core.event.message.TestMessage;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHub {
    private static Logger logger = LoggerFactory.getLogger(MessageHub.class.getCanonicalName());

    @Subscribe
    public void applicationEvent(TestMessage message) {
        // handle event
        logger.info("test message");
    }
}
