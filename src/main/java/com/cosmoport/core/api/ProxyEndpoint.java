package com.cosmoport.core.api;

import com.cosmoport.core.dto.ProxyRequestDto;
import com.cosmoport.core.dto.ResultSuccessDto;
import com.cosmoport.core.event.message.FireUpGateMessage;
import com.google.common.eventbus.EventBus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public final class ProxyEndpoint {
    private final EventBus eventBus;

    public ProxyEndpoint(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostMapping
    public ResultSuccessDto reactOn(@RequestBody ProxyRequestDto request) {
        if (request.name().equals("fire_gate")) {
            eventBus.post(new FireUpGateMessage(request.event(), request.type()));
        }
        return new ResultSuccessDto("success");
    }
}
