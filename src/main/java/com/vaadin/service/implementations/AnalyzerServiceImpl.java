package com.vaadin.service.implementations;

import com.vaadin.domain.Currency;
import com.vaadin.domain.ExchangePortal;
import com.vaadin.exceptions.NoExchangePortalFoundException;
import com.vaadin.service.interfaces.AnalyzerService;
import com.vaadin.service.interfaces.ExchangePortalService;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class AnalyzerServiceImpl implements AnalyzerService {

    private final ExchangePortalService exchangePortalService;

    @Override
    public ExchangePortal findMinRatio(Currency currency) throws NoExchangePortalFoundException {
        List<ExchangePortal> exchangePortals = exchangePortalService.getExchangePortalsWithCurrency(currency);

            ExchangePortal minExchangePortal = exchangePortals.stream()
                    .min(Comparator.comparing(ExchangePortal::getRatio))
                    .orElseThrow(()
                            -> new NoExchangePortalFoundException("Exchange portal not found"));
        return minExchangePortal;
    }

    @Override
    public ExchangePortal findMaxRatio(Currency currency) throws NoExchangePortalFoundException {
        List<ExchangePortal> exchangePortals = exchangePortalService.getExchangePortalsWithCurrency(currency);

        ExchangePortal minExchangePortal = exchangePortals.stream()
                .max(Comparator.comparing(ExchangePortal::getRatio))
                .orElseThrow(()
                        -> new NoExchangePortalFoundException("Exchange portal not found"));
        return minExchangePortal;
    }

    @Override
    public ExchangePortal findOldestRatio(Currency currency) throws NoExchangePortalFoundException {
        List<ExchangePortal> exchangePortals = exchangePortalService.getExchangePortalsWithCurrency(currency);

        ExchangePortal minExchangePortal = exchangePortals.stream()
                .min(Comparator.comparing(ExchangePortal::getTime))
                .orElseThrow(()
                        -> new NoExchangePortalFoundException("Exchange portal not found"));
        return minExchangePortal;
    }

    @Override
    public ExchangePortal findNewestRatio(Currency currency) throws  NoExchangePortalFoundException{
        List<ExchangePortal> exchangePortals = exchangePortalService.getExchangePortalsWithCurrency(currency);

        ExchangePortal minExchangePortal = exchangePortals.stream()
                .max(Comparator.comparing(ExchangePortal::getTime))
                .orElseThrow(()
                        -> new NoExchangePortalFoundException("Exchange portal not found"));
        return minExchangePortal;
    }
}
