package com.vaadin.client.coinlayer;

import com.vaadin.client.ApiService;
import com.vaadin.domain.Currency;
import com.vaadin.domain.ExchangePortal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
@RequiredArgsConstructor
public class CoinLayerService implements ApiService {

    @Autowired
    private final CoinLayerClient coinLayerClient;
    @Autowired
    private final CoinLayerConfig coinLayerConfig;

    @Override
    public ExchangePortal createExchangePortal(Currency currency) {
        CoinLayerResponse coinLayerResponse = coinLayerClient.getCryptoReadings(currency);
        LocalDateTime now = LocalDateTime.now();

        ExchangePortal exchangePortal = ExchangePortal.builder()
                .provider(coinLayerConfig.getNAME())
                .currencyToBuy(currency)
                .currencyToPay(Currency.USD)
                .ratio(coinLayerResponse.getRates().get(currency.toString()))
                .time(now)
                .build();

        return exchangePortal;
    }

}
