package com.vaadin.client.nomics;

import com.vaadin.client.ApiService;
import com.vaadin.domain.Currency;
import com.vaadin.domain.ExchangePortal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class NomicsService implements ApiService {

    @Autowired
    private final NomicsClient nomicsClient;
    @Autowired
    private final NomicsConfig nomicsConfig;

    @Override
    public ExchangePortal createExchangePortal(Currency currency) {
        List<NomicsResponse> nomicsResponses = nomicsClient.getCryptoReadings(currency);

        LocalDateTime now = LocalDateTime.now();

        ExchangePortal exchangePortal = ExchangePortal.builder()
                .provider(nomicsConfig.getNAME())
                .currencyToBuy(currency)
                .currencyToPay(Currency.USD)
                .ratio(Double.valueOf(nomicsResponses.get(0).getPrice()))
                .time(now)
                .build();

        return exchangePortal;
    }

}
