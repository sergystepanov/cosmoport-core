package com.cosmoport.core.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Static files endpoint of the application.
 *
 * @since 1.0.0
 */
@RestController
public class StaticsEndpoint {
    @GetMapping("/favicon.ico")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void getFavicon() {
    }
}
