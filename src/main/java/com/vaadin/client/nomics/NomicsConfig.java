package com.vaadin.client.nomics;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class NomicsConfig {
    @Value("${nomics.api.endpoint}")
    private String endpoint;

    @Value("${nomics.api.accessKey}")
    private String accessKey;

    @Value("${nomics.api.name}")
    private String NAME;
}
