package com.vaadin.client;

import com.vaadin.dto.ItemToBuyDto;
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
public class ItemToBuyClient {
    @Autowired
    private ClientConfig clientConfig;

    @Autowired
    private RestTemplate restTemplate;

    public URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "/items/")
                .build().encode().toUri();
        return url;
    }

    public List<ItemToBuyDto> getItemToBuys() {
        try {
            ItemToBuyDto[] boardsResponse = restTemplate.getForObject(getUrl(), ItemToBuyDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new ItemToBuyDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public ItemToBuyDto getItemToBuyById(Long itemToBuyId) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "/items/" + itemToBuyId)
                .build().encode().toUri();
        try {
            ItemToBuyDto boardsResponse = restTemplate.getForObject(url, ItemToBuyDto.class);
            return boardsResponse;
        } catch (RestClientException e) {
            return new ItemToBuyDto();
        }
    }

    public ItemToBuyDto createItemToBuy(ItemToBuyDto itemToBuyDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "items/")
                .build().encode().toUri();
        return restTemplate.postForObject(url, itemToBuyDto, ItemToBuyDto.class);
    }


    public void deleteItemToBuy(Long itemToBuyId) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "items/" + itemToBuyId)
                .build().encode().toUri();
        restTemplate.delete(url);
    }

    public void finalizeItemToBuy(Long itemToBuyId, Long walletId) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "items/finalize/" + walletId)
                .queryParam("itemToBuyId", itemToBuyId)
                .build().encode().toUri();
        restTemplate.getForObject(url, ItemToBuyDto.class);
    }

}
