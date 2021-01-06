package com.vaadin.repository;

import com.vaadin.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Override
    <S extends Wallet> S save(S Wallet);

    @Query
    List<Wallet> retrieveWallets();

    @Query
    List<Long> retrieveWalletsId();


    Optional<Wallet> findByName(String name);
}
