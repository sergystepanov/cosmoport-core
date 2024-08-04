package com.cosmoport.core.api;

import com.cosmoport.core.dto.DateTimeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * Server time and date endpoint.
 *
 * @since 0.0.1
 */
@RestController
@RequestMapping("/time")
public class DateTimeEndpoint {
    @GetMapping
    public DateTimeDto getDateTime() {
        return new DateTimeDto(Instant.now().getEpochSecond());
    }
}
