package com.cosmoport.core.api;

import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.dto.TimeSuggestionDto;
import com.cosmoport.core.dto.request.TicketsUpdateRequestDto;
import com.cosmoport.core.event.ReloadMessage;
import com.cosmoport.core.event.SyncTimetablesMessage;
import com.cosmoport.core.persistence.SettingsPersistenceService;
import com.cosmoport.core.persistence.TimetablePersistenceService;
import com.cosmoport.core.persistence.exception.ValidationException;
import com.cosmoport.core.service.SuggestionService;
import com.cosmoport.core.sync.RemoteSync;
import com.cosmoport.core.sync.Types;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/timetable")
public final class TimetableEndpoint {
    private final TimetablePersistenceService service;
    private final SettingsPersistenceService settings;
    private final SuggestionService suggestionService;
    private final ApplicationEventPublisher bus;

    public TimetableEndpoint(TimetablePersistenceService service,
                             SettingsPersistenceService settings,
                             SuggestionService suggestionService,
                             ApplicationEventPublisher bus) {
        this.service = service;
        this.settings = settings;
        this.suggestionService = suggestionService;
        this.bus = bus;
    }

    @GetMapping
    public List<EventDto> get(@RequestParam(value = "date", required = false) String date,
                              @RequestParam(value = "date2", required = false)String date2,
                              @RequestParam(value = "gate", defaultValue = "0") long gateId) {
        return date2 != null && (!Objects.equals(date2, "")) ?
                service.getAllFromDates(date, date2) :
                service.getAllWithFilter(date, gateId);
    }

    @GetMapping("/all")
    public List<EventDto> getAll(
            @RequestParam("page") int page,
            @RequestParam("count") int count,
            @RequestParam(value = "date", required = false) String date) {
        final List<EventDto> events = service.getAllPage(page, count);
        // We assume that this method will be called on every timetable app opening
        bus.publishEvent(new SyncTimetablesMessage(this));

        return events;
    }

    /**
     * Gets the event with {@code id} and one event after that for same gate.
     *
     * @param id long An id of event.
     * @return Two events.
     */
    @GetMapping("/byIdAndOneAfter")
    public List<EventDto> getEvents(@RequestParam("id") long id) {
        return service.getCustomByIdForGate(id);
    }

    @PostMapping
    public EventDto create(@RequestBody EventDto event) {
        final EventDto newEvent = service.save(event);
        bus.publishEvent(new ReloadMessage(this));
        new RemoteSync(settings.getSyncServerParams()).process(Types.CREATE, newEvent);

        return newEvent;
    }

    @PostMapping("/update/event")
    public EventDto update(@RequestBody EventDto event) {
        final EventDto newEvent = service.save(event);
        bus.publishEvent(new ReloadMessage(this));
        new RemoteSync(settings.getSyncServerParams()).process(Types.UPDATE, newEvent);

        return newEvent;
    }

    /**
     * Updates the number of sold tickets. Same as {@link SyncEndpoint::updateTickets}.
     *
     * @param request An object containing an events' id and its new tickets count value.
     * @return A result object.
     * @throws RuntimeException In case of any errors.
     */
    @PostMapping("/tickets")
    public ResultDto updateTickets(@RequestBody TicketsUpdateRequestDto request) throws RuntimeException {
        service.updateTicketsForced(request.id(), request.tickets(), request.forceOpen());

        return new ResultDto(true);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") final long id) {
        final String deleted = "{\"deleted\": " + service.delete(id) + '}';
        bus.publishEvent(new ReloadMessage(this));
        new RemoteSync(settings.getSyncServerParams()).process(Types.DELETE, new EventDto(id));

        return deleted;
    }

    // Suggestions
    @GetMapping("/suggest/next")
    public TimeSuggestionDto getSuggestion(@RequestParam("gate") long gateId,
                                           @RequestParam("date") final String date) throws RuntimeException {
        if (!(gateId > 0)) {
            throw new ValidationException("Set the gate number.");
        }

        final String date_ = date != null && !date.isEmpty() ? date :
                new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        return suggestionService.suggest(gateId, date_);
    }
}
