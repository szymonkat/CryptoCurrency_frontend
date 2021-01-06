/*
package com.vaadin.mapper;

import com.vaadin.domain.ExchangePortal;
import com.vaadin.dto.ExchangePortalDto;
import com.vaadin.service.interfaces.ItemToBuyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExchangePortalMapper {

    private final ItemToBuyMapper itemToBuyMapper;
    private final ItemToBuyService itemToBuyService;

    public ExchangePortal mapToExchangePortal(ExchangePortalDto exchangePortalDto) {
        if (exchangePortalDto.getItemToBuyDtoId() == null) {
            return new ExchangePortal(exchangePortalDto.getId(), exchangePortalDto.getProvider(),
                    exchangePortalDto.getCurrencyToBuy(), exchangePortalDto.getCurrencyToPay(),
                    exchangePortalDto.getRatio(), exchangePortalDto.getTime());
        } else {
            return new ExchangePortal(exchangePortalDto.getId(), exchangePortalDto.getProvider(),
                    exchangePortalDto.getCurrencyToBuy(), exchangePortalDto.getCurrencyToPay(),
                    exchangePortalDto.getRatio(), exchangePortalDto.getTime(),
                    itemToBuyService.findItemToBuyById(exchangePortalDto.getItemToBuyDtoId()));
        }
    }

    public ExchangePortalDto mapToExchangePortalDto(ExchangePortal exchangePortal) {
        if (exchangePortal.getItemToBuy() == null) {
            return new ExchangePortalDto(exchangePortal.getId(), exchangePortal.getProvider(),
                    exchangePortal.getCurrencyToBuy(),
                    exchangePortal.getCurrencyToPay(), exchangePortal.getRatio(), exchangePortal.getTime());
        } else {
            return new ExchangePortalDto(exchangePortal.getId(), exchangePortal.getProvider(),
                exchangePortal.getCurrencyToBuy(),
                exchangePortal.getCurrencyToPay(), exchangePortal.getRatio(), exchangePortal.getTime(),
                    exchangePortal.getItemToBuy().getId());
        }
    }

    public List<ExchangePortalDto> mapToExchangePortalDtoList(List<ExchangePortal> exchangePortalList) {
        return exchangePortalList.stream()
                .map(this ::mapToExchangePortalDto)
                .collect(Collectors.toList());
    }

    public List<ExchangePortal> mapToExchangePortalList(List<ExchangePortalDto> exchangePortalDtos) {
        return exchangePortalDtos.stream()
                .map(this ::mapToExchangePortal)
                .collect(Collectors.toList());
    }

}
*/
