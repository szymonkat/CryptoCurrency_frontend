package com.vaadin.service.interfaces;

import com.vaadin.domain.Currency;
import com.vaadin.domain.ExchangePortal;

import java.util.List;

public interface ExchangePortalService {
    ExchangePortal findExchangePortalById(final Long ExchangePortalId);
    List<ExchangePortal> getExchangePortals();
    List<ExchangePortal> getExchangePortalsWithCurrency(final Currency currency);
    ExchangePortal save (final ExchangePortal ExchangePortal);
    void delete(final Long exchangePortalId);
}
