package com.vaadin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedNativeQuery(
        name = "ExchangePortal.retrieveExchangePortals",
        query = "SELECT * FROM exchange_portals",
        resultClass = ExchangePortal.class
)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "EXCHANGE_PORTALS")
public class ExchangePortal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String provider;

    @Column
    private Currency currencyToBuy;

    @Column
    private Currency currencyToPay;

    @Column
    private Double ratio;

    @Column
    private LocalDateTime time;

    @OneToOne(mappedBy = "exchangePortal")
    private ItemToBuy itemToBuy;

    public ExchangePortal(String provider, Currency currencyToBuy, Currency currencyToPay, Double ratio, LocalDateTime time) {
        this.provider = provider;
        this.currencyToBuy = currencyToBuy;
        this.currencyToPay = currencyToPay;
        this.ratio = ratio;
        this.time = time;
    }

    public ExchangePortal(Long id, String provider, Currency currencyToBuy, Currency currencyToPay, Double ratio, LocalDateTime time) {
        this.id = id;
        this.provider = provider;
        this.currencyToBuy = currencyToBuy;
        this.currencyToPay = currencyToPay;
        this.ratio = ratio;
        this.time = time;
    }

    public static ExchangePortalBuilder builder() {
        return new ExchangePortalBuilder();
    }

    @Override
    public String toString() {
        return "Provider: " + provider + ", id = " + id + ", " + currencyToBuy + ", " + ratio;
    }

    public static class ExchangePortalBuilder {
        private Long id;
        private String provider;
        private Currency currencyToBuy;
        private Currency currencyToPay;
        private Double ratio;
        private LocalDateTime time;
        private ItemToBuy itemToBuy;

        ExchangePortalBuilder() {
        }

        public ExchangePortalBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ExchangePortalBuilder provider(String provider) {
            this.provider = provider;
            return this;
        }

        public ExchangePortalBuilder currencyToBuy(Currency currencyToBuy) {
            this.currencyToBuy = currencyToBuy;
            return this;
        }

        public ExchangePortalBuilder currencyToPay(Currency currencyToPay) {
            this.currencyToPay = currencyToPay;
            return this;
        }

        public ExchangePortalBuilder ratio(Double ratio) {
            this.ratio = ratio;
            return this;
        }

        public ExchangePortalBuilder time(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public ExchangePortalBuilder itemToBuy(ItemToBuy itemToBuy) {
            this.itemToBuy = itemToBuy;
            return this;
        }

        public ExchangePortal build() {
            return new ExchangePortal(id, provider, currencyToBuy, currencyToPay, ratio, time, itemToBuy);
        }

        public String toString() {
            return "ExchangePortal.ExchangePortalBuilder(id=" + this.id + ", provider=" + this.provider + ", currencyToBuy=" + this.currencyToBuy + ", currencyToPay=" + this.currencyToPay + ", ratio=" + this.ratio + ", time=" + this.time + ", itemToBuy=" + this.itemToBuy + ")";
        }
    }
}
