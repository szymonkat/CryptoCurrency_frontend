package com.vaadin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemToBuyDto {

    private Long id;
    private Long exchangePortalId;
    private Double quantityToBuy;

    public ItemToBuyDto(Long exchangePortalId, Double quantityToBuy) {
        this.exchangePortalId = exchangePortalId;
        this.quantityToBuy = quantityToBuy;
    }
}
