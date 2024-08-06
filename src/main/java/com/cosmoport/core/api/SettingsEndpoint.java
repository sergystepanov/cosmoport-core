package com.cosmoport.core.api;

import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.dto.SettingsDto;
import com.cosmoport.core.dto.request.TextValueUpdateRequestDto;
import com.cosmoport.core.event.ReloadMessage;
import com.cosmoport.core.persistence.SettingsPersistenceService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
public final class SettingsEndpoint {
    private final SettingsPersistenceService service;
    private final ApplicationEventPublisher bus;

    public SettingsEndpoint(SettingsPersistenceService settingsPersistenceService,
                            ApplicationEventPublisher bus) {
        this.service = settingsPersistenceService;
        this.bus = bus;
    }

    @GetMapping
    public List<SettingsDto> getSettings() {
        return service.getAllWithoutProtectedValues();
    }

    @PostMapping("/update/{id}")
    public ResultDto updateSetting(@PathVariable("id") long id, @RequestBody TextValueUpdateRequestDto requestDto) {
        final boolean updated = service.updateSettingForId(id, requestDto.text());
        if (updated) {
            bus.publishEvent(new ReloadMessage(this));
        }

        return new ResultDto(updated);
    }
}
