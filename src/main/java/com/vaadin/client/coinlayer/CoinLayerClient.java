package com.vaadin.client.coinlayer;

import com.vaadin.domain.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class CoinLayerClient {

    @Autowired
    private CoinLayerConfig coinLayerConfig;

    @Autowired
    private RestTemplate restTemplate;

    public URI getUrl(Currency currency) {
        URI url = UriComponentsBuilder.fromHttpUrl(coinLayerConfig.getEndpoint())
                .queryParam("access_key", coinLayerConfig.getAccessKey())
                .queryParam("symbols", currency).build().encode().toUri();
        return url;
    }

    public CoinLayerResponse getCryptoReadings(Currency currency) {
        return restTemplate.postForObject(getUrl(currency), null, CoinLayerResponse.class);
    }
}
