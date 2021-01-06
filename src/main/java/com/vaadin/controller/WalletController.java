package com.vaadin.controller;

import com.vaadin.dto.WalletDto;
import com.vaadin.mapper.WalletMapper;
import com.vaadin.service.interfaces.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletMapper walletMapper;
    private final WalletService walletService;

    @GetMapping
    public List<WalletDto> getAllWallet() {
        return walletMapper.mapToWalletDtoList(walletService.getWallets());
    }

    @GetMapping("{walletId}")
    public WalletDto getWalletById(@PathVariable Long walletId) {
        return walletMapper.mapToWalletDto(walletService.findWalletById(walletId));
    }

    @PostMapping
    public WalletDto createWallet(@RequestBody WalletDto walletDto) {
        return walletMapper.mapToWalletDto(walletService.createWallet
                (walletMapper.mapToWallet(walletDto)));
    }

    @PutMapping
    public WalletDto updateWallet(@RequestBody WalletDto walletDto) {
        return walletMapper.mapToWalletDto(walletService.updateWallet
                (walletMapper.mapToWallet(walletDto)));
    }

    @DeleteMapping("{walletId}")
    public void deleteWallet(@PathVariable Long walletId) {
        walletService.deleteWallet(walletId);
    }

    @GetMapping("/test/{walletId}")
    public void testHowManyUsdWalletHas(@PathVariable Long walletId) {
        System.out.println(walletService.checkHowManyUsdWalletHas(walletId));
    }
}
