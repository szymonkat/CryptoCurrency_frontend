package com.vaadin.client.nomics;

import com.vaadin.domain.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class NomicsClient {

    @Autowired
    private NomicsConfig nomicsConfig;

    @Autowired
    private RestTemplate restTemplate;

    public URI getUrl(Currency currency) {
        URI url = UriComponentsBuilder.fromHttpUrl(nomicsConfig.getEndpoint() + "currencies/ticker")
                .queryParam("key", nomicsConfig.getAccessKey())
                .queryParam("ids", currency).build().encode().toUri();
        return url;
    }

    public List<NomicsResponse> getCryptoReadings(Currency currency) {
        NomicsResponse[] boardsResponse = restTemplate.postForObject(getUrl(currency), null, NomicsResponse[].class);

        if (boardsResponse != null) {
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();
    }






}