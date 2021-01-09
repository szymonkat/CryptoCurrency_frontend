/*
package com.vaadin.mapper;

import com.vaadin.client.WalletClient;
import com.vaadin.client.WalletItemClient;
import com.vaadin.domain.Wallet;
import com.vaadin.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WalletMapper {

   // private final WalletItemMapper walletItemMapper;

    public Wallet mapToWallet(WalletDto walletDto) {
        if (walletDto.getId() == null) {
            return new Wallet(walletDto.getName(), new ArrayList<>());
        } else  {
            return new Wallet(walletDto.getId(), walletDto.getName(), new ArrayList<>());
        }
    }

    public WalletDto mapToWalletDto(Wallet wallet) {
        return new WalletDto(wallet.getId(), wallet.getName(), new ArrayList<>());
    }

    public List<Wallet> mapToWalletList(List<WalletDto> walletDtoList) {
        return walletDtoList.stream()
                .map(this ::mapToWallet)
                .collect(Collectors.toList());
    }

    public List<WalletDto> mapToWalletDtoList(List<Wallet> walletList) {
        return walletList.stream()
                .map(this ::mapToWalletDto)
                .collect(Collectors.toList());
    }
}

//walletItemMapper.mapToWalletItemDtoList(wallet.getWalletItemList())

*/
