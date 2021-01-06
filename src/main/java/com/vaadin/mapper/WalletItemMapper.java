package com.vaadin.mapper;

import com.vaadin.domain.WalletItem;
import com.vaadin.dto.WalletItemDto;
import com.vaadin.service.interfaces.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WalletItemMapper {

    private final WalletService walletService;

    public WalletItem mapToWalletItem(WalletItemDto walletItemDto) {
        if (walletItemDto.getId() == null) {
            return new WalletItem(walletService.findWalletById(walletItemDto.getWalletId()),
                    walletItemDto.getCurrency(), walletItemDto.getQuantity());
        } else {
            return new WalletItem(walletItemDto.getId(),
                    walletService.findWalletById(walletItemDto.getWalletId()),
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
