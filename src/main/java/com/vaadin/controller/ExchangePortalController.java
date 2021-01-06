package com.vaadin.controller;

import com.vaadin.client.ApiService;
import com.vaadin.client.ServiceFactory;
import com.vaadin.domain.Currency;
import com.vaadin.domain.ExchangePortal;
import com.vaadin.dto.ExchangePortalDto;
import com.vaadin.mapper.ExchangePortalMapper;
import com.vaadin.service.interfaces.ExchangePortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/exchange")
@RequiredArgsConstructor
public class ExchangePortalController {

    private final ServiceFactory serviceFactory;
    private final ExchangePortalMapper exchangePortalMapper;
    private final ExchangePortalService exchangePortalService;

    @GetMapping
    public ExchangePortalDto createNewResponse(@RequestParam Currency currency,@RequestParam String serviceName) {
        ApiService apiService = serviceFactory.createService(serviceName);
        return exchangePortalMapper.mapToExchangePortalDto(apiService.createExchangePortal(currency));
    }

    @GetMapping("/get")
    public List<ExchangePortalDto> getExchangePortals() {
        return exchangePortalMapper.mapToExchangePortalDtoList(exchangePortalService.getExchangePortals());
    }
}
