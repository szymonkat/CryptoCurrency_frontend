package com.vaadin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@NamedNativeQuery(
        name = "Wallet.retrieveWallets",
        query = "SELECT * FROM wallets",
        resultClass = Wallet.class
)

@NamedNativeQuery(
        name = "Wallet.retrieveWalletsId",
        query = "SELECT id FROM wallets"
)
@Entity(name = "WALLETS")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @OneToMany(targetEntity = WalletItem.class,
            fetch = FetchType.EAGER,
            mappedBy = "wallet",
            cascade = CascadeType.ALL)
    private List<WalletItem> walletItemList;

    public Wallet(String name) {
        this.name = name;
    }

    public Wallet(String name, List<WalletItem> walletItemList) {
        this.name = name;
        this.walletItemList = walletItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wallet wallet = (Wallet) o;

        if (!id.equals(wallet.id)) return false;
        if (!name.equals(wallet.name)) return false;
        return Objects.equals(walletItemList, wallet.walletItemList);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (walletItemList != null ? walletItemList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Wallet id " + id + ", owner's name= " + '\'' + name + '\'';
    }
}
