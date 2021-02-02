package com.vaadin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletDto {
    private Long id;
    private String name;
    private List<WalletItemDto> walletItemList;

    public WalletDto(String name) {
        this.name = name;
    }

    public WalletDto(String name, List<WalletItemDto> walletItemList) {
        this.name = name;
        this.walletItemList = walletItemList;
    }

    @Override
    public String toString() {
        return "Wallet: " +
                "id=" + id +
                ", name='" + name + "'";
    }
}
