package com.vaadin.config;

import com.vaadin.service.implementations.*;
import com.vaadin.service.interfaces.*;
import com.vaadin.repository.ExchangePortalRepository;
import com.vaadin.repository.ItemToBuyRepository;
import com.vaadin.repository.WalletItemRepository;
import com.vaadin.repository.WalletRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@Configuration
public class ServiceConfig {

    @Bean
    protected WalletItemService walletItemService(WalletItemRepository walletItemRepository,
                                                  WalletService walletService) {
        return new WalletItemServiceImpl(walletItemRepository, walletService);
    }

    @Bean
    protected WalletService walletService(WalletRepository walletRepository) {
        return new WalletServiceImpl(walletRepository);
    }

    @Bean
    protected ExchangePortalService exchangePortalService(ExchangePortalRepository exchangePortalRepository) {
        return new ExchangePortalServiceImpl(exchangePortalRepository);
    }

    @Bean
    protected ItemToBuyService itemToBuyService(ItemToBuyRepository itemToBuyRepository,
                                                WalletService walletService, WalletItemService walletItemService,
                                                ExchangePortalService exchangePortalService) {
        return new ItemToBuyServiceImpl(itemToBuyRepository, walletService, walletItemService, exchangePortalService);
    }

    @Bean
    protected AnalyzerService analyzerService(ExchangePortalService exchangePortalService) {
        return new AnalyzerServiceImpl(exchangePortalService);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
