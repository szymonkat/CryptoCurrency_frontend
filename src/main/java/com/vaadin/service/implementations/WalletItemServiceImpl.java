package com.vaadin.service.implementations;

import com.vaadin.domain.Currency;
import com.vaadin.domain.Wallet;
import com.vaadin.domain.WalletItem;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.repository.WalletItemRepository;
import com.vaadin.service.interfaces.WalletItemService;
import com.vaadin.service.interfaces.WalletService;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class WalletItemServiceImpl implements WalletItemService {

    private final WalletItemRepository walletItemRepository;
    private final WalletService walletService;

    @Override
    public WalletItem modifyWalletItem(WalletItem walletItem) {
        if (walletItemRepository.existsByCurrencyAndWallet(walletItem.getCurrency(), walletItem.getWallet())) {
            WalletItem walletItemDraft = walletItemRepository.findByCurrencyAndWallet(walletItem.getCurrency(), walletItem.getWallet());
            Double newQuantity = walletItemDraft.getQuantity() + walletItem.getQuantity();
            walletItemDraft.setQuantity(newQuantity);
            return save(walletItemDraft);
        } else {
           return walletItemRepository.save(walletItem);
        }
    }

    @Override
    public WalletItem updateWalletItem(WalletItem walletItem) throws NotFoundException {
        WalletItem returnedWalletItem = findWalletItemById(walletItem.getId());
        returnedWalletItem.setWallet(walletItem.getWallet());
        returnedWalletItem.setCurrency(walletItem.getCurrency());
        returnedWalletItem.setQuantity(walletItem.getQuantity());
        save(returnedWalletItem);
        return returnedWalletItem;
    }

    @Override
    public WalletItem findWalletItemById(Long walletPositionId) {
        return walletItemRepository.findById(walletPositionId).orElseThrow(
                () -> new NotFoundException("Does not exist"));
    }

    @Override
    public List<WalletItem> getWalletItems() {
        return walletItemRepository.findAll();
    }

    @Override
    public void deleteWalletItem(Long id) {
        findWalletItemById(id);
        walletItemRepository.deleteById(id);
    }

    @Override
    public WalletItem save(WalletItem walletItem) {
        return walletItemRepository.save(walletItem);
    }

    @Override
    public WalletItem returnUsdWalletItem(Long walletId) throws NotFoundException {
        Wallet wallet = walletService.findWalletById(walletId);
        WalletItem walletItemUsd = new WalletItem();

        try {
            walletItemUsd = wallet.getWalletItemList().stream()
                    .filter(n -> n.getCurrency().equals(Currency.USD))
                    .findAny()
                    .get();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return walletItemUsd;
    }

    @Override
    public WalletItem returnCurrencyWalletItem(Long walletId, Currency currency) throws NotFoundException {

        Wallet wallet = walletService.findWalletById(walletId);
        WalletItem walletItem = new WalletItem();
        try {
            walletItem = wallet.getWalletItemList().stream()
                    .filter(n -> n.getCurrency().equals(currency))
                    .findAny()
                    .get();
        } catch (Exception e) {
            walletItem.setCurrency(currency);
            walletItem.setQuantity(0.0);
            walletItem.setWallet(wallet);
            modifyWalletItem(walletItem);
        }
        return walletItem;
    }

    public List<WalletItem> getWalletItemsSql() {
        final List<WalletItem> list = walletItemRepository.retrieveWalletItems();
        return list;
    }
}
