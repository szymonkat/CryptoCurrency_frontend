package com.vaadin.service.implementations;

import com.vaadin.domain.Currency;
import com.vaadin.domain.Wallet;
import com.vaadin.domain.WalletItem;
import com.vaadin.exceptions.WalletNotEmptyException;
import com.vaadin.exceptions.WalletWithThatNameAlreadyExistException;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.repository.WalletRepository;
import com.vaadin.service.interfaces.WalletService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(
                () -> new NotFoundException("Wallet with id: " + walletId + " does not exist"));
    }

    @Override
    public List<Wallet> getWallets() {
        return walletRepository.findAll();
    }

    @Override
    public Wallet createWallet(Wallet wallet) throws WalletWithThatNameAlreadyExistException {
        if (walletRepository.findByName(wallet.getName()).equals(Optional.empty())) {
            return walletRepository.save(wallet);
        } else {
            throw new WalletWithThatNameAlreadyExistException("Wallet with owner's name: " + wallet.getName()
                    + " already exists");
        }
    }

    @Override
    public Wallet updateWallet(Wallet wallet) throws NotFoundException {
        Wallet returnedWallet = walletRepository.findById(wallet.getId())
                .orElseThrow(() -> new NotFoundException("Wallet with id: " + wallet.getId() + " does not exist"));
        returnedWallet.setName(wallet.getName());
        returnedWallet.setWalletItemList(wallet.getWalletItemList());
        save(returnedWallet);
        return returnedWallet;
    }

    @Override
    public void deleteWallet(Long id) throws WalletNotEmptyException {
        Wallet wallet = findWalletById(id);
        if (wallet.getWalletItemList().isEmpty()) {
            walletRepository.deleteById(id);
        } else {
            throw new WalletNotEmptyException("Wallet with id: " + id + " is not empty," +
                    " please withdraw wallet items first ");
        }
    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public List<Wallet> getWalletsSql() {
        final List<Wallet> list = walletRepository.retrieveWallets();
        return list;
    }

    @Override
    public boolean checkIfExistsById(Long walletId) {
        return walletRepository.existsById(walletId);
    }

    @Override
    public Double checkHowManyUsdWalletHas(Long walletId)  throws NotFoundException {
        Wallet wallet = findWalletById(walletId);
        Double fundsUsd = 0.0;
        try {
            WalletItem walletItemUsd = wallet.getWalletItemList().stream()
                    .filter(n -> n.getCurrency().equals(Currency.USD))
                    .findAny()
                    .get();
            fundsUsd = walletItemUsd.getQuantity();
        } catch (Exception e) {
            fundsUsd = 0.0;
        }
        return fundsUsd;
    }



    public List<Long> getWalletsId() {
        final List<Long> list = walletRepository.retrieveWalletsId();
        return list;
    }

}
