/*
package com.vaadin.mapper;

import com.vaadin.client.WalletClient;
import com.vaadin.domain.Wallet;
import com.vaadin.domain.WalletItem;
import com.vaadin.dto.WalletItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WalletItemMapper {

    @Autowired
    private WalletClient walletClient;
    @Autowired
    private WalletMapper walletMapper;

    public WalletItem mapToWalletItem(WalletItemDto walletItemDto) {
        if (walletItemDto.getId() == null) {
            return new WalletItem(
                    walletMapper.mapToWallet(walletClient.getWalletById(walletItemDto.getWalletId())),
                    walletItemDto.getCurrency(),
                    walletItemDto.getQuantity());
        } else {
            return new WalletItem(walletItemDto.getId(),
                    walletMapper.mapToWallet(walletClient.getWalletById(walletItemDto.getWalletId())),
                    walletItemDto.getCurrency(),
                    walletItemDto.getQuantity());
        }
    }

    public WalletItemDto mapToWalletItemDto(WalletItem walletItem) {
        return new WalletItemDto(walletItem.getId(),
                walletItem.getWallet().getId(),
                walletItem.getCurrency(),
                walletItem.getQuantity());
    }

    public List<WalletItemDto> mapToWalletItemDtoList(List<WalletItem> walletItemList) {
        return walletItemList.stream()
                .map(this ::mapToWalletItemDto)
                .collect(Collectors.toList());
    }

    public List<WalletItem> mapToWalletItemList(List<WalletItemDto> walletItemDtoList) {
        return walletItemDtoList.stream()
                .map(this ::mapToWalletItem)
                .collect(Collectors.toList());
    }
}
*/
