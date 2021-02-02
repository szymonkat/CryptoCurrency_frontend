package com.vaadin.client;

import com.vaadin.domain.Currency;
import com.vaadin.dto.ExchangePortalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class AnalyzerClient {

    @Autowired
    private ClientConfig clientConfig;

    @Autowired
    private RestTemplate restTemplate;


    public ExchangePortalDto getMinValueExchangePortal(Currency currency) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "analyzer/min/")
                .queryParam("currency", currency)
                .build().encode().toUri();
        try {
            ExchangePortalDto boardsResponse = restTemplate.getForObject(url, ExchangePortalDto.class);
            return boardsResponse;
        } catch (RestClientException e) {
            return new ExchangePortalDto();
        }
    }

    public ExchangePortalDto getMaxValueExchangePortal(Currency currency) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "analyzer/max/")
                .queryParam("currency", currency)
                .build().encode().toUri();
        try {
            ExchangePortalDto boardsResponse = restTemplate.getForObject(url, ExchangePortalDto.class);
            return boardsResponse;
        } catch (RestClientException e) {
            return new ExchangePortalDto();
        }
    }

    public ExchangePortalDto getOldestValueExchangePortal(Currency currency) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "analyzer/old/")
                .queryParam("currency", currency)
                .build().encode().toUri();
        try {
            ExchangePortalDto boardsResponse = restTemplate.getForObject(url, ExchangePortalDto.class);
            return boardsResponse;
        } catch (RestClientException e) {
            return new ExchangePortalDto();
        }
    }

    public ExchangePortalDto getNewestValueExchangePortal(Currency currency) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "analyzer/min/")
                .queryParam("currency", currency)
                .build().encode().toUri();
        try {
            ExchangePortalDto boardsResponse = restTemplate.getForObject(url, ExchangePortalDto.class);
            return boardsResponse;
        } catch (RestClientException e) {
            return new ExchangePortalDto();
        }
    }

}
