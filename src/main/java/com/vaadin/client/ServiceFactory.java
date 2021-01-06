package com.vaadin.client;

import com.vaadin.client.coinlayer.CoinLayerClient;
import com.vaadin.client.coinlayer.CoinLayerConfig;
import com.vaadin.client.coinlayer.CoinLayerService;
import com.vaadin.client.nomics.NomicsClient;
import com.vaadin.client.nomics.NomicsConfig;
import com.vaadin.client.nomics.NomicsService;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory {

    @Autowired
    private CoinLayerConfig coinLayerConfig;
    @Autowired
    private CoinLayerClient coinLayerClient;

    @Autowired
    private NomicsConfig nomicsConfig;
    @Autowired
    private NomicsClient nomicsClient;

    public static final String COIN_LAYER = "coinlayer";
    public static final String NOMICS = "nomics";

    public ApiService createService(final String serviceName) throws NotFoundException {
        switch (serviceName) {
            case COIN_LAYER:
                return new CoinLayerService(coinLayerClient, coinLayerConfig);
            case NOMICS:
                return new NomicsService(nomicsClient, nomicsConfig);
            default:
                throw new NotFoundException("Please type \'coinlayer\' or \'nomics\'," +
                        " otherwise Your request cannot be " +
                        "processed");
        }
    }
}
