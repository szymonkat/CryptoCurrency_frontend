package com.vaadin.service.interfaces;

import com.vaadin.domain.Currency;
import com.vaadin.domain.ExchangePortal;

import java.util.List;

public interface AnalyzerService {
    ExchangePortal findMinRatio(Currency currency);
    ExchangePortal findMaxRatio(Currency currency);
    ExchangePortal findOldestRatio(Currency currency);
    ExchangePortal findNewestRatio(Currency currency);
}
