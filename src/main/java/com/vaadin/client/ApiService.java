package com.vaadin.client;

import com.vaadin.domain.Currency;
import com.vaadin.domain.ExchangePortal;

public interface ApiService {
    public ExchangePortal createExchangePortal(Currency currency);
}
