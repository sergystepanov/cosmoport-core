package com.cosmoport.core.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class EventStatusPersistenceServiceTest extends PersistenceTest {
    @Test
    void map() {

    }

    @Test
    @DisplayName("Should be able to execute getAll()")
    void getAll() {
        EventStatusPersistenceService service =
                new EventStatusPersistenceService(getLogger(), getDataSourceProvider());

        Assertions.assertEquals(7, service.getAll().size());
    }
}