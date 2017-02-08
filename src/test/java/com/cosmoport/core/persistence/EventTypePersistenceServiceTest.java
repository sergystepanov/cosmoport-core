package com.cosmoport.core.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class EventTypePersistenceServiceTest extends PersistenceTest {
    @Test
    void map() {

    }

    @Test
    @DisplayName("Should be able to execute getAll()")
    void getAll() {
        EventTypePersistenceService service =
                new EventTypePersistenceService(getLogger(), getDataSource());

        Assertions.assertEquals(4, service.getAll().size());
    }
}