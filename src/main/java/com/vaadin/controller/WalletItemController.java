package com.vaadin.controller;

import com.vaadin.dto.WalletItemDto;
import com.vaadin.mapper.WalletItemMapper;
import com.vaadin.service.interfaces.WalletItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/walletItem")
@RequiredArgsConstructor
public class WalletItemController {

    private final WalletItemMapper walletItemMapper;
    private final WalletItemService walletItemService;

    @GetMapping
    public List<WalletItemDto> getAllWalletItem() {
        return walletItemMapper.mapToWalletItemDtoList(walletItemService.getWalletItems());
    }

    @GetMapping("{walletItemId}")
    public WalletItemDto getWalletItemById(@PathVariable Long walletItemId) {
        return walletItemMapper.mapToWalletItemDto(walletItemService.findWalletItemById(walletItemId));
    }

    @PostMapping
    public WalletItemDto modifyWalletItem(@RequestBody WalletItemDto walletItemDto) {
        return walletItemMapper.mapToWalletItemDto(walletItemService.modifyWalletItem
                (walletItemMapper.mapToWalletItem(walletItemDto)));
    }

    @PutMapping
    public WalletItemDto updateWalletPosition(@RequestBody WalletItemDto walletItemDto) {
        return walletItemMapper.mapToWalletItemDto(walletItemService.updateWalletItem
                (walletItemMapper.mapToWalletItem(walletItemDto)));
    }

    @DeleteMapping("{walletItemId}")
    public void deleteWalletItem(@PathVariable Long walletItemId) {
        walletItemService.deleteWalletItem(walletItemId);
    }
}
