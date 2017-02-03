package com.cosmoport.core.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class TimetablePersistenceServiceTest extends PersistenceTest {
    private TimetablePersistenceService service;

    @Test
    @DisplayName("Should be able to execute getAll()")
    public void getAll() throws Exception {
        service = new TimetablePersistenceService(getLogger(), getDataSource());

        Assertions.assertEquals(10, service.getAll().size());
    }
}