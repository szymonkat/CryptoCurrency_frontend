package com.vaadin.domain;

import com.vaadin.dto.WalletDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemFinalize {
    Long idValue;
    WalletDto walletDto;
}
