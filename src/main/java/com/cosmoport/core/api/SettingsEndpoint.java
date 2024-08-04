package com.cosmoport.core.api;

import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.dto.SettingsDto;
import com.cosmoport.core.dto.request.TextValueUpdateRequestDto;
import com.cosmoport.core.event.message.ReloadMessage;
import com.cosmoport.core.persistence.SettingsPersistenceService;
import com.google.common.eventbus.EventBus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
public final class SettingsEndpoint {
    private final SettingsPersistenceService service;
    private final EventBus eventBus;

    public SettingsEndpoint(SettingsPersistenceService settingsPersistenceService, EventBus eventBus) {
        this.service = settingsPersistenceService;
        this.eventBus = eventBus;
    }

    @GetMapping
    public List<SettingsDto> getSettings() {
        return service.getAllWithoutProtectedValues();
    }

    @PostMapping("/update/{id}")
    public ResultDto updateSetting(@PathVariable("id") long id, @RequestBody TextValueUpdateRequestDto requestDto) {
        final boolean updated = service.updateSettingForId(id, requestDto.text());
        if (updated) {
            eventBus.post(new ReloadMessage());
        }

        return new ResultDto(updated);
    }
}
