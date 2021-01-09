package com.vaadin.dto;

import com.vaadin.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExchangePortalDto {

    private Long id;
    private String provider;
    private Currency currencyToBuy;
    private Currency currencyToPay;
    private Double ratio;
    private LocalDateTime time;
    private Long itemToBuyDtoId;

    public ExchangePortalDto(String provider, Currency currencyToBuy, Currency currencyToPay, Double ratio, LocalDateTime time) {
        this.provider = provider;
        this.currencyToBuy = currencyToBuy;
        this.currencyToPay = currencyToPay;
        this.ratio = ratio;
        this.time = time;
    }

    public ExchangePortalDto(Long id, String provider, Currency currencyToBuy, Currency currencyToPay, Double ratio, LocalDateTime time) {
        this.id = id;
        this.provider = provider;
        this.currencyToBuy = currencyToBuy;
        this.currencyToPay = currencyToPay;
        this.ratio = ratio;
        this.time = time;
    }

    @Override
    public String toString() {
        return "ExchangePortal: " +
                "id=" + id +
                ", provider='" + provider + '\'' +
                ", currencyToBuy=" + currencyToBuy +
                ", currencyToPay=" + currencyToPay +
                ", ratio=" + ratio;
    }
}
