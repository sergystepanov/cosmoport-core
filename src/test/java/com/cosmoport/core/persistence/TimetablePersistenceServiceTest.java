package com.cosmoport.core.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class TimetablePersistenceServiceTest extends PersistenceTest {
    @Test
    @DisplayName("Should be able to execute getAll()")
    public void getAll() throws Exception {
        final TimetablePersistenceService service =
                new TimetablePersistenceService(getLogger(), getDataSourceProvider());

        Assertions.assertEquals(10, service.getAll().size());
    }

    @Test
    @DisplayName("Should be able execute getAllWithFilter()")
    void getAllWithFilter() {
        final TimetablePersistenceService service =
                new TimetablePersistenceService(getLogger(), getDataSourceProvider());

        Assertions.assertEquals(10, service.getAllWithFilter(null, null).size());
        Assertions.assertEquals(10, service.getAllWithFilter("2017-02-05", null).size());
        Assertions.assertEquals(10, service.getAllWithFilter(null, 1L).size());
        Assertions.assertEquals(0, service.getAllWithFilter("2015-01-01", 1L).size());
    }
}