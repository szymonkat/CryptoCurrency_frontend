package com.vaadin.client;

import com.vaadin.dto.WalletItemDto;
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
public class WalletItemClient {

    @Autowired
    private ClientConfig clientConfig;

    @Autowired
    private RestTemplate restTemplate;

    public URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "/walletItem/")
                .build().encode().toUri();
        return url;
    }

    public List<WalletItemDto> getWalletItems() {
        try {
            WalletItemDto[] boardsResponse = restTemplate.getForObject(getUrl(), WalletItemDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new WalletItemDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public WalletItemDto getWalletItemById(Long walletItemId) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "/walletItem/" + walletItemId)
                .build().encode().toUri();
        try {
            WalletItemDto boardsResponse = restTemplate.getForObject(url, WalletItemDto.class);
            return boardsResponse;
        } catch (RestClientException e) {
            return new WalletItemDto();
        }
    }

    public WalletItemDto createWalletItem(WalletItemDto walletItemDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "walletItem/")
                .build().encode().toUri();
        return restTemplate.postForObject(url, walletItemDto, WalletItemDto.class);
    }

    public void updateWalletItem(WalletItemDto walletItemDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "walletItem/")
                .build().encode().toUri();
        restTemplate.put(url, walletItemDto);
    }

    public void deleteWalletItem(Long walletItemId) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "walletItem/" + walletItemId)
                .build().encode().toUri();
        restTemplate.delete(url);
    }

}
