package com.vaadin.service.interfaces;

import com.vaadin.domain.Wallet;
import com.vaadin.flow.router.NotFoundException;

import java.util.List;

public interface WalletService {
    Wallet createWallet(final Wallet wallet);
    Wallet updateWallet(final Wallet wallet) throws NotFoundException;
    Wallet findWalletById(final Long walletId);
    boolean checkIfExistsById(final Long walletId);
    List<Wallet> getWallets();
    void deleteWallet(final Long id);
    Double checkHowManyUsdWalletHas(final Long walletId);
    Wallet save(final Wallet wallet);
}
