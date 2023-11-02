package com.cosmoport.core.dto;

import java.util.List;

public record EventReferenceDataDto(List<EventTypeDto> types, List<EventStatusDto> statuses, List<EventStateDto> states,
                                    List<EventDestinationDto> destinations) {
}
