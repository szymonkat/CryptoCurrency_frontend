package com.vaadin.service.interfaces;

import com.vaadin.domain.Currency;
import com.vaadin.domain.WalletItem;
import com.vaadin.flow.router.NotFoundException;

import java.util.List;


public interface WalletItemService {

    WalletItem modifyWalletItem(final WalletItem walletItem);
    WalletItem updateWalletItem(final WalletItem walletItem) throws NotFoundException;
    WalletItem findWalletItemById(final Long walletItemId);
    List<WalletItem> getWalletItems();
    void deleteWalletItem(final Long id);
    WalletItem save (WalletItem walletItem);
    WalletItem returnUsdWalletItem(final Long walletId) throws NotFoundException;
    WalletItem returnCurrencyWalletItem(final Long walletId,final Currency currency) throws NotFoundException;
}
