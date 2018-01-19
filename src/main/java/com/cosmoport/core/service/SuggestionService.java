package com.cosmoport.core.service;

import com.cosmoport.core.dto.TimeSuggestionDto;
import com.cosmoport.core.persistence.TimetablePersistenceService;
import com.google.inject.Inject;

public final class SuggestionService {
    private final TimetablePersistenceService db;

    @Inject
    public SuggestionService(TimetablePersistenceService db) {
        this.db = db;
    }

    public TimeSuggestionDto suggest(final long gateId, final String date) {
        return new TimeSuggestionDto(db.getLastTimeForGate(gateId, date));
    }
}
