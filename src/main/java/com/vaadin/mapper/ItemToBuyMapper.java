/*
package com.vaadin.mapper;

import com.vaadin.domain.ItemToBuy;
import com.vaadin.dto.ItemToBuyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemToBuyMapper {



    public ItemToBuy mapToItemToBuy(ItemToBuyDto itemToBuyDto) {
        if (itemToBuyDto.getId() == null) {
            return new ItemToBuy(
                    exchangePortalService.findExchangePortalById(itemToBuyDto.getExchangePortalId()),
                    itemToBuyDto.getQuantityToBuy());
        } else {
            return new ItemToBuy(itemToBuyDto.getId(),
                    exchangePortalService.findExchangePortalById(itemToBuyDto.getExchangePortalId()),
                    itemToBuyDto.getQuantityToBuy());
        }
    }

    public ItemToBuyDto mapToItemToBuyDto(ItemToBuy itemToBuy) {
        return new ItemToBuyDto(itemToBuy.getId(),
                itemToBuy.getExchangePortal().getId(),
                itemToBuy.getQuantityToBuy());
    }

    public List<ItemToBuyDto> mapToItemToBuyDtoList(List<ItemToBuy> itemToBuyList) {
        return itemToBuyList.stream()
                .map(this ::mapToItemToBuyDto)
                .collect(Collectors.toList());
    }

    public List<ItemToBuy> mapToItemToBuyList(List<ItemToBuyDto> itemToBuyDtoList) {
        return itemToBuyDtoList.stream()
                .map(this ::mapToItemToBuy)
                .collect(Collectors.toList());
    }

}
*/
