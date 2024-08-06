package com.cosmoport.core.api;

import com.cosmoport.core.dto.ProxyRequestDto;
import com.cosmoport.core.dto.ResultSuccessDto;
import com.cosmoport.core.event.FireUpGateMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public final class ProxyEndpoint {
    private final ApplicationEventPublisher bus;

    public ProxyEndpoint(ApplicationEventPublisher bus) {
        this.bus = bus;
    }

    @PostMapping
    public ResultSuccessDto reactOn(@RequestBody ProxyRequestDto request) {
        if (request.name().equals("fire_gate")) {
            bus.publishEvent(new FireUpGateMessage(this, request.event(), request.type()));
        }
        return new ResultSuccessDto("success");
    }
}
