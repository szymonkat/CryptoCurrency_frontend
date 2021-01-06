package com.vaadin.repository;

import com.vaadin.domain.Currency;
import com.vaadin.domain.Wallet;
import com.vaadin.domain.WalletItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {

    @Override
    <S extends WalletItem> S save(S WalletItem);

    @Query
    List<WalletItem> retrieveWalletItems();

    Boolean existsByCurrencyAndWallet(Currency currency, Wallet wallet);
    WalletItem findByCurrencyAndWallet(Currency currency, Wallet wallet);

}
