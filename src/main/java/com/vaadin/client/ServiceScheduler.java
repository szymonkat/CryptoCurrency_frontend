package com.vaadin.client;

import com.vaadin.domain.Currency;
import com.vaadin.service.interfaces.ExchangePortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ServiceScheduler {

    public static final String COIN_LAYER = "coinlayer";
    public static final String NOMICS = "nomics";
    public static final Currency XMR = Currency.XMR;
    public static final Currency BTC = Currency.BTC;

    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    ExchangePortalService exchangePortalService;

    @Scheduled(cron = "0 0 19 * * ?", zone = "Europe/Paris")
    public void downloadCurrencyRatioCoinLayer() {
        ApiService apiService = serviceFactory.createService(COIN_LAYER);
        exchangePortalService.save(apiService.createExchangePortal(XMR));
    }

    @Scheduled(cron = "0 0 19 * * ?", zone = "Europe/Paris")
    public void downloadCurrencyRatioNomics() {
        ApiService apiService = serviceFactory.createService(NOMICS);
        exchangePortalService.save(apiService.createExchangePortal(BTC));
    }
}
