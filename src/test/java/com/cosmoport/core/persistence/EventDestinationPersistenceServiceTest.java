package com.cosmoport.core.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class EventDestinationPersistenceServiceTest extends PersistenceTest {
    @Test
    @DisplayName("Should be able to execute getAll()")
    void getAll() {
        EventDestinationPersistenceService service =
                new EventDestinationPersistenceService(getLogger(), getDataSourceProvider());

        Assertions.assertEquals(4, service.getAll().size());
    }

    @Test
    void map() {

    }
}