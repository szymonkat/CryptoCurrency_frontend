package com.vaadin.controller;

import com.vaadin.domain.Currency;
import com.vaadin.dto.ExchangePortalDto;
import com.vaadin.mapper.ExchangePortalMapper;
import com.vaadin.service.interfaces.AnalyzerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/analyzer")
@RequiredArgsConstructor
public class AnalyzerController {

    private final AnalyzerService analyzerService;
    private final ExchangePortalMapper exchangePortalMapper;

    @GetMapping(value = "/min")
    public ExchangePortalDto findMin(@RequestParam Currency currency) {
        return exchangePortalMapper.mapToExchangePortalDto(analyzerService.findMinRatio(currency));
    }

    @GetMapping(value = "/max")
    public ExchangePortalDto findMax(@RequestParam Currency currency) {
        return exchangePortalMapper.mapToExchangePortalDto(analyzerService.findMaxRatio(currency));
    }

    @GetMapping(value = "/old")
    public ExchangePortalDto findOldest(@RequestParam Currency currency) {
        return exchangePortalMapper.mapToExchangePortalDto(analyzerService.findOldestRatio(currency));
    }

    @GetMapping(value = "/young")
    public ExchangePortalDto findYoungest(@RequestParam Currency currency) {
        return exchangePortalMapper.mapToExchangePortalDto(analyzerService.findNewestRatio(currency));
    }


}
