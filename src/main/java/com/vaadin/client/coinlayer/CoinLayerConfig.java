package com.vaadin.client.coinlayer;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CoinLayerConfig {
    @Value("${coinLayer.api.endpoint}")
    private String endpoint;

    @Value("${coinLayer.api.accessKey}")
    private String accessKey;

    @Value("${coinLayer.api.name}")
    private String NAME;
}
