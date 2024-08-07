package com.cosmoport.core.api;

import com.cosmoport.core.api.error.ApiAuthError;
import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.dto.SyncAddEventDto;
import com.cosmoport.core.dto.SyncTicketsDto;
import com.cosmoport.core.dto.request.HasAuthKey;
import com.cosmoport.core.event.ReloadMessage;
import com.cosmoport.core.persistence.SettingsPersistenceService;
import com.cosmoport.core.persistence.TimetablePersistenceService;
import com.cosmoport.core.persistence.constant.Constants;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync")
public final class SyncEndpoint {
    private final TimetablePersistenceService timetable;
    private final SettingsPersistenceService settingsPersistenceService;
    private final ApplicationEventPublisher bus;

    public SyncEndpoint(TimetablePersistenceService timetable,
                        SettingsPersistenceService settingsPersistenceService,
                        ApplicationEventPublisher bus) {
        this.timetable = timetable;
        this.settingsPersistenceService = settingsPersistenceService;
        this.bus = bus;
    }

    private void auth(HasAuthKey syncRequest) throws ApiAuthError {
        final boolean isKeyOk = settingsPersistenceService.paramEquals(Constants.syncServerKey, syncRequest.key());

        if (!isKeyOk) {
            throw new ApiAuthError();
        }
    }

    @PostMapping("/tickets")
    public ResultDto syncTickets(@RequestBody SyncTicketsDto syncTickets) throws ApiAuthError, RuntimeException {
        auth(syncTickets);

        timetable.updateTickets(syncTickets.eventId(), syncTickets.value());

        return new ResultDto(true);
    }

    @PostMapping("/add/event")
    public EventDto create(@RequestBody SyncAddEventDto syncAddEvent) {
        final EventDto newEvent = timetable.save(syncAddEvent.event());
        bus.publishEvent(new ReloadMessage(this));

        return newEvent;
    }
}
