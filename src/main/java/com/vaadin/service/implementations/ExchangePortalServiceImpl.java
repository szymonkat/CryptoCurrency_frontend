package com.vaadin.service.implementations;

import com.vaadin.domain.Currency;
import com.vaadin.domain.ExchangePortal;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.repository.ExchangePortalRepository;
import com.vaadin.service.interfaces.ExchangePortalService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ExchangePortalServiceImpl implements ExchangePortalService {

    private final ExchangePortalRepository exchangePortalRepository;

    @Override
    public ExchangePortal findExchangePortalById(Long exchangePortalId) {
        return exchangePortalRepository.findById(exchangePortalId)
                .orElseThrow(() -> new NotFoundException("Exchange Portal with id: " + exchangePortalId + " does not exist"));
    }

    @Override
    public List<ExchangePortal> getExchangePortals() {
        return exchangePortalRepository.findAll();
    }

    @Override
    public ExchangePortal save(ExchangePortal exchangePortal) {
        return exchangePortalRepository.save(exchangePortal);
    }

    @Override
    public List<ExchangePortal> getExchangePortalsWithCurrency(Currency currency) {
        return getExchangePortals().stream()
                .filter(n -> n.getCurrencyToBuy() == currency)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long exchangePortalId) {
       findExchangePortalById(exchangePortalId);
       exchangePortalRepository.deleteById(exchangePortalId);
    }

    public List<ExchangePortal> getExchangePortalsSql() {
        final List<ExchangePortal> list = exchangePortalRepository.retrieveExchangePortals();
        return list;
    }
}
