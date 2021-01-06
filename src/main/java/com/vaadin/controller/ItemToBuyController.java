package com.vaadin.controller;

import com.vaadin.dto.ItemToBuyDto;
import com.vaadin.mapper.ItemToBuyMapper;
import com.vaadin.service.interfaces.ItemToBuyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/items")
@RequiredArgsConstructor
public class ItemToBuyController {

    private final ItemToBuyService itemToBuyService;
    private final ItemToBuyMapper itemToBuyMapper;

    @GetMapping
    public List<ItemToBuyDto> getAllItemsToBuy() {
        return itemToBuyMapper.mapToItemToBuyDtoList(itemToBuyService.getItemToBuys());
    }

    @GetMapping("{itemToBuyId}")
    public ItemToBuyDto getItemToBuyById(@PathVariable Long itemToBuyId) {
        return itemToBuyMapper.mapToItemToBuyDto(itemToBuyService.findItemToBuyById(itemToBuyId));
    }

    @PostMapping
    public ItemToBuyDto createItemToBuy(@RequestBody ItemToBuyDto itemToBuyDto) {
        return itemToBuyMapper.mapToItemToBuyDto(itemToBuyService.createItemToBuy(
                (itemToBuyMapper.mapToItemToBuy(itemToBuyDto))));
    }

    @PutMapping
    public ItemToBuyDto updateItemToBuy(@RequestBody ItemToBuyDto itemToBuyDto) {
        return itemToBuyMapper.mapToItemToBuyDto(itemToBuyService.updateItemToBuy
                (itemToBuyMapper.mapToItemToBuy(itemToBuyDto)));
    }

    @GetMapping("/finalize/{walletId}")
    public void finalizeItemToBuy(@PathVariable Long walletId, @RequestParam Long itemToBuyId) {
        itemToBuyService.finalizeItemToBuy(itemToBuyId, walletId);
    }

    @DeleteMapping("{itemToBuyId}")
    public void deleteItemToBuy(@PathVariable Long itemToBuyId) {
        itemToBuyService.deleteItemToBuy(itemToBuyId);
    }
}
