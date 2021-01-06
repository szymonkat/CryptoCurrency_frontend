package com.vaadin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ITEMS_TO_BUY")
public class ItemToBuy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ExchangePortal exchangePortal;

    @Column
    private Double quantityToBuy;

    public ItemToBuy(ExchangePortal exchangePortal, Double quantityToBuy) {
        this.exchangePortal = exchangePortal;
        this.quantityToBuy = quantityToBuy;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", exchangePortal=" + exchangePortal.getProvider();
    }
}
