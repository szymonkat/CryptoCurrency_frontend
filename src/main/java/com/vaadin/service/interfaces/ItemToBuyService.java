package com.vaadin.service.interfaces;

import com.vaadin.domain.ItemToBuy;
import com.vaadin.flow.router.NotFoundException;

import java.util.List;

public interface ItemToBuyService {
    List<ItemToBuy> getItemToBuys();
    ItemToBuy createItemToBuy(final ItemToBuy itemToBuy);
    ItemToBuy updateItemToBuy(final ItemToBuy itemToBuy) throws NotFoundException;
    ItemToBuy findItemToBuyById(final Long itemToBuyId);
    void deleteItemToBuy(Long id);
    ItemToBuy save (final ItemToBuy itemToBuy);
    void finalizeItemToBuy(final Long itemToBuyId,final Long walletId);
    boolean checkIfExists(Long itemToBuyId);
}
