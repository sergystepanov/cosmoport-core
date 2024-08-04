package com.cosmoport.core.api;

import com.cosmoport.core.dto.GateDto;
import com.cosmoport.core.persistence.GatePersistenceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gates")
public class GateEndpoint {
    private final GatePersistenceService gatePersistenceService;

    public GateEndpoint(GatePersistenceService gatePersistenceService) {
        this.gatePersistenceService = gatePersistenceService;
    }

    @GetMapping
    public List<GateDto> getAll() {
        return gatePersistenceService.getAll();
    }
}
