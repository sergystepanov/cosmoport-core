package com.cosmoport.core.api;

import com.cosmoport.core.dto.PasswordDto;
import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.persistence.SettingsPersistenceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public final class AuthEndpoint {
    private final SettingsPersistenceService service;

    public AuthEndpoint(SettingsPersistenceService settingsPersistenceService) {
        this.service = settingsPersistenceService;
    }

    @PostMapping("/check")
    public ResultDto check(@RequestBody PasswordDto password) {
        return new ResultDto(service.paramEquals("password", password.pwd()));
    }

    @PostMapping("/set")
    public ResultDto set(@RequestBody PasswordDto password) {
        return new ResultDto(service.updateSettingForParam("password", password.pwd()));
    }
}
