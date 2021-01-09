package com.vaadin.client;

import com.vaadin.dto.WalletDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class WalletClient {

    @Autowired
    private ClientConfig clientConfig;

    @Autowired
    private RestTemplate restTemplate;

    public URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "/wallet/")
                .build().encode().toUri();
        return url;
    }

    public List<WalletDto> getWallets() {
        try {
            WalletDto[] boardsResponse = restTemplate.getForObject(getUrl(), WalletDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new WalletDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public WalletDto getWalletById(Long walletId) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "/wallet/" + walletId)
                .build().encode().toUri();
        try {
            WalletDto boardsResponse = restTemplate.getForObject(url, WalletDto.class);
            return boardsResponse;
        } catch (RestClientException e) {
            return new WalletDto();
        }
    }

    public WalletDto createWallet(WalletDto walletDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "wallet/")
                .build().encode().toUri();
        return restTemplate.postForObject(url, walletDto, WalletDto.class);
    }

    public void updateWallet(WalletDto walletDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "wallet/")
                .build().encode().toUri();
        restTemplate.put(url, walletDto);
    }

    public void deleteWallet(Long walletId) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "wallet/" + walletId)
                .build().encode().toUri();
        restTemplate.delete(url);
    }

}
