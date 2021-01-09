package com.vaadin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemToBuyDto {

    private Long id;
    private Long exchangePortalId;
    private Double quantityToBuy;

    public ItemToBuyDto(Long exchangePortalId, Double quantityToBuy) {
        this.exchangePortalId = exchangePortalId;
        this.quantityToBuy = quantityToBuy;
    }

    @Override
    public String toString() {
        return "ItemToBuyDto: " +
                "id=" + id +
                ", exchangePortalId=" + exchangePortalId +
                ", quantityToBuy=" + quantityToBuy;
    }
}
