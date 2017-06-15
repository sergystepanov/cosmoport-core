package com.cosmoport.core.api;

import com.cosmoport.core.api.error.ApiAuthError;
import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.dto.SyncAddEventDto;
import com.cosmoport.core.dto.SyncTicketsDto;
import com.cosmoport.core.dto.request.HasAuthKey;
import com.cosmoport.core.event.message.ReloadMessage;
import com.cosmoport.core.persistence.SettingsPersistenceService;
import com.cosmoport.core.persistence.TimetablePersistenceService;
import com.cosmoport.core.persistence.constant.Constants;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sync")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public final class SyncEndpoint {
    private final TimetablePersistenceService timetable;
    private final SettingsPersistenceService settingsPersistenceService;
    private final EventBus eventBus;

    @Inject
    public SyncEndpoint(TimetablePersistenceService timetable, SettingsPersistenceService settingsPersistenceService,
                        EventBus eventBus) {
        this.timetable = timetable;
        this.settingsPersistenceService = settingsPersistenceService;
        this.eventBus = eventBus;
    }

    private void auth(HasAuthKey syncRequest) throws ApiAuthError {
        final boolean isKeyOk = settingsPersistenceService.paramEquals(Constants.syncServerKey, syncRequest.getKey());

        if (!isKeyOk) {
            throw new ApiAuthError();
        }
    }

    @POST
    @Path("/tickets")
    public ResultDto syncTickets(final SyncTicketsDto syncTickets) throws ApiAuthError, RuntimeException {
        auth(syncTickets);

        timetable.updateTickets(syncTickets.getEventId(), syncTickets.getValue());

        return new ResultDto(true);
    }

    @POST
    @Path("/add/event")
    public EventDto create(final SyncAddEventDto syncAddEvent) {
        final EventDto newEvent = timetable.save(syncAddEvent.getEvent());
        eventBus.post(new ReloadMessage());

        return newEvent;
    }
}
