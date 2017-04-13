package com.cosmoport.core.api;

import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.persistence.PersistenceTest;
import com.cosmoport.core.persistence.TimetablePersistenceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimetableEndpointTest extends PersistenceTest {
    private TimetablePersistenceService service;
    private EventBus eventBus;
    private ObjectMapper mapper;

    @BeforeEach
    public void before() {
        super.before();

        service = new TimetablePersistenceService(getLogger(), getDataSourceProvider());
        eventBus = new EventBus();
        mapper = new ObjectMapper();
    }

    @Test
    void get() throws Exception {
        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();

        dispatcher.getRegistry().addSingletonResource(new TimetableEndpoint(service, eventBus));

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(MockHttpRequest.get("/timetable"), response);

        List<EventDto> result = mapper.readValue(response.getContentAsString(),
                new TypeReference<List<EventDto>>() {
                });
        assertEquals(10, result.size());
    }
}
