package com.vaadin.controller;

import com.google.gson.Gson;
import com.vaadin.client.ApiService;
import com.vaadin.client.ServiceFactory;

import com.vaadin.client.nomics.NomicsClient;
import com.vaadin.client.nomics.NomicsResponse;
import com.vaadin.client.nomics.NomicsService;
import com.vaadin.domain.Currency;
import com.vaadin.domain.ExchangePortal;
import com.vaadin.dto.ExchangePortalDto;
import com.vaadin.mapper.ExchangePortalMapper;
import com.vaadin.service.interfaces.ExchangePortalService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ExchangePortalController.class)
public class ExchangePortalControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceFactory serviceFactory;

    @MockBean
    private ExchangePortalMapper exchangePortalMapper;

    @MockBean
    private ExchangePortalService exchangePortalService;

    @MockBean
    private ApiService apiService;

//    @MockBean
//    private NomicsService nomicsService;

    @MockBean
    private NomicsClient nomicsClient;

    @Test
    public void getEmptyExchangePortalTest() throws Exception {
        //Given
        List<ExchangePortalDto> exchangePortalDtoList = new ArrayList<>();
        when(exchangePortalMapper.mapToExchangePortalDtoList(exchangePortalService.getExchangePortals())).thenReturn(exchangePortalDtoList);
        //When & Then
        mockMvc.perform(get("/v1/exchange/get").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) // or isOk()
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // Not working

    @Test
    public void getExchangePortalTest() throws Exception {
        //Given
        ExchangePortal exchangePortal = new ExchangePortal(1L, "nomics", Currency.XMR,
                Currency.USD,160.00, LocalDateTime.of(2020, 12, 20, 20, 50));
        ExchangePortalDto exchangePortalDto = new ExchangePortalDto(1L, "nomics", Currency.XMR,
                Currency.USD,160.00, LocalDateTime.of(2020, 12, 20, 20, 50));
        Currency currency = Currency.XMR;
        String serviceName = "nomics";
        String currencyString = "XMR";

        when(apiService.createExchangePortal(currency)).thenReturn(exchangePortal);
        when(serviceFactory.createService(serviceName)).thenReturn(apiService);
        when(exchangePortalMapper.mapToExchangePortalDto(apiService.createExchangePortal(currency)))
                .thenReturn(exchangePortalDto);

        //When & Then
        mockMvc.perform(get("/v1/exchange").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("currency", currencyString)
                .param("serviceName", serviceName))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
