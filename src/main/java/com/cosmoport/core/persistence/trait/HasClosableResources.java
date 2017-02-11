package com.cosmoport.core.persistence.trait;

import org.slf4j.Logger;

public interface HasClosableResources {
    default void close(AutoCloseable resource) {
        if (resource == null) {
            return;
        }

        try {
            resource.close();
        } catch (Exception e) {
            getLogger().error("Couldn't close resource, {}", e.getMessage());
        }
    }

    default void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            close(resource);
        }
    }

    Logger getLogger();
}
