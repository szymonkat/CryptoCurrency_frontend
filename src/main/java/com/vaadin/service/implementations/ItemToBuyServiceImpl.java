package com.vaadin.service.implementations;

import com.vaadin.domain.*;
import com.vaadin.exceptions.ExchangePortalIsAlreadyTakenException;
import com.vaadin.exceptions.ExchangePortalPriceTooOldException;
import com.vaadin.exceptions.NotEnoughUsdInTheWalletException;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.repository.ItemToBuyRepository;
import com.vaadin.service.interfaces.ExchangePortalService;
import com.vaadin.service.interfaces.ItemToBuyService;
import com.vaadin.service.interfaces.WalletItemService;
import com.vaadin.service.interfaces.WalletService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class ItemToBuyServiceImpl implements ItemToBuyService {

    private final ItemToBuyRepository itemToBuyRepository;
    private final WalletService walletService;
    private final WalletItemService walletItemService;
    private final ExchangePortalService exchangePortalService;

    @Override
    public ItemToBuy createItemToBuy(ItemToBuy itemToBuy) {
        return itemToBuyRepository.findById(itemToBuy.getId()).orElse(itemToBuyRepository.save(itemToBuy));
    }

    @Override
    public ItemToBuy updateItemToBuy(ItemToBuy itemToBuy) throws NotFoundException {
        ItemToBuy returnedItemToBuy = findItemToBuyById(itemToBuy.getId());
        returnedItemToBuy.setExchangePortal(itemToBuy.getExchangePortal());
        returnedItemToBuy.setQuantityToBuy(itemToBuy.getQuantityToBuy());
        save(returnedItemToBuy);
        return returnedItemToBuy;
    }

    @Override
    public ItemToBuy findItemToBuyById(Long itemToBuyId) {
        return itemToBuyRepository.findById(itemToBuyId).orElseThrow(
                () -> new NotFoundException("Item to buy with id " + itemToBuyId + " does not exist"));
    }

    @Override
    public List<ItemToBuy> getItemToBuys() {
        return itemToBuyRepository.findAll();
    }

    @Override
    public void deleteItemToBuy(Long id) {
        findItemToBuyById(id);
        itemToBuyRepository.deleteById(id);
    }

    @Override
    public ItemToBuy save(ItemToBuy itemToBuy) throws ExchangePortalPriceTooOldException {
        ExchangePortal exchangePortal = exchangePortalService.findExchangePortalById(itemToBuy.getExchangePortal().getId());
        if (exchangePortal.getItemToBuy() == null) {
            return itemToBuyRepository.save(itemToBuy);
        } else {
            throw new ExchangePortalPriceTooOldException("You cannot create Item to Buy with Exchange Portal already taken");
        }
    }

    @Override
    public void finalizeItemToBuy(Long itemToBuyId, Long walletId) throws NotFoundException, ExchangePortalPriceTooOldException,
            NotEnoughUsdInTheWalletException {
        ItemToBuy itemToBuy = findItemToBuyById(itemToBuyId);

            if (checkIfTimeIsNotOlderThen20Min(itemToBuy.getExchangePortal())
                && checkIfWalletHasSufficientFunds(walletId, itemToBuy)) {
                //1) Subtract USD amount
                WalletItem walletItem = walletItemService.returnUsdWalletItem(walletId);
                Double oldValue = walletItem.getQuantity();
                Double costValue = itemToBuy.getQuantityToBuy() * itemToBuy.getExchangePortal().getRatio();
                walletItem.setQuantity(oldValue-costValue);
                walletItemService.save(walletItem);
                //2) Add bought wallet item
                WalletItem addWalletItem = walletItemService.returnCurrencyWalletItem(walletId, itemToBuy.getExchangePortal().getCurrencyToBuy());
                Double oldCurrencyValue = addWalletItem.getQuantity();
                Double newCurrencyValue = oldCurrencyValue + itemToBuy.getQuantityToBuy();
                addWalletItem.setQuantity(newCurrencyValue);
                walletItemService.save(addWalletItem);
                //3) Delete itemToBuy
                deleteItemToBuy(itemToBuyId);
                // Check that Exchange Portal is still displayed
            } else { // Error handling
                if (!checkIfTimeIsNotOlderThen20Min(itemToBuy.getExchangePortal()))
                    throw new ExchangePortalPriceTooOldException("Your exchange Portal price might be too old (20 min validation)");
                if (!checkIfWalletHasSufficientFunds(walletId, itemToBuy))
                    throw new NotEnoughUsdInTheWalletException("You don't have sufficient funds in USD");
                else throw new NotFoundException();
            }
    }

    @Override
    public boolean checkIfExists(Long itemToBuyId) {
        return itemToBuyRepository.existsById(itemToBuyId);
    }

    private boolean checkIfTimeIsNotOlderThen20Min(ExchangePortal exchangePortal) {
        LocalDateTime tenMinutesOld = LocalDateTime.now().minusMinutes(20);
        return exchangePortal.getTime().isAfter(tenMinutesOld);
    }

    private boolean checkIfWalletHasSufficientFunds(Long walletId, ItemToBuy itemToBuy) {
        Double walletUsdAmount = walletService.checkHowManyUsdWalletHas(walletId);
            if (itemToBuy.getQuantityToBuy() * itemToBuy.getExchangePortal().getRatio() < walletUsdAmount) return true;
        return false;
    }

    private void checkFind(Long walletId, Long itemToBuyId) {
        System.out.println("test");
        finalizeItemToBuy(itemToBuyId, walletId);
    }
}