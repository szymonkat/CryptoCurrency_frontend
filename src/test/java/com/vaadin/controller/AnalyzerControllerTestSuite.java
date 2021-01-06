package com.vaadin.controller;

import com.vaadin.domain.Currency;
import com.vaadin.dto.ExchangePortalDto;
import com.vaadin.mapper.ExchangePortalMapper;
import com.vaadin.service.interfaces.AnalyzerService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AnalyzerController.class)
public class AnalyzerControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyzerService analyzerService;

    @MockBean
    private ExchangePortalMapper exchangePortalMapper;

    @Test
    public void getMinValueTest() throws Exception {
        //Given
        ExchangePortalDto exchangePortalDto = new ExchangePortalDto(1L, "nomics", Currency.XMR,
                Currency.USD,160.00, LocalDateTime.of(2020, 12, 20, 20, 50));

        when(exchangePortalMapper.mapToExchangePortalDto(analyzerService.findMinRatio(Currency.XMR))).thenReturn(exchangePortalDto);

        //When & Then
        mockMvc.perform(get("/v1/analyzer/min").contentType(MediaType.APPLICATION_JSON)
                .param("currency", "XMR")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.provider", is("nomics")))
                .andExpect(jsonPath("$.currencyToBuy", is("XMR")))
                .andExpect(jsonPath("$.currencyToPay", is("USD")))
                .andExpect(jsonPath("$.ratio", is(160.0)))
                .andExpect(jsonPath("$.time", is("2020-12-20T20:50:00")));
    }

    @Test
    public void getMaxValueTest() throws Exception {
        //Given
        ExchangePortalDto exchangePortalDto = new ExchangePortalDto(1L, "nomics", Currency.XMR,
                Currency.USD,160.00, LocalDateTime.of(2020, 12, 20, 20, 50));

        when(exchangePortalMapper.mapToExchangePortalDto(analyzerService.findMinRatio(Currency.XMR))).thenReturn(exchangePortalDto);

        //When & Then
        mockMvc.perform(get("/v1/analyzer/max").contentType(MediaType.APPLICATION_JSON)
                .param("currency", "XMR")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.provider", is("nomics")))
                .andExpect(jsonPath("$.currencyToBuy", is("XMR")))
                .andExpect(jsonPath("$.currencyToPay", is("USD")))
                .andExpect(jsonPath("$.ratio", is(160.0)))
                .andExpect(jsonPath("$.time", is("2020-12-20T20:50:00")));
    }

    @Test
    public void getOldestValueTest() throws Exception {
        //Given
        ExchangePortalDto exchangePortalDto = new ExchangePortalDto(1L, "nomics", Currency.XMR,
                Currency.USD,160.00, LocalDateTime.of(2020, 12, 20, 20, 50));

        when(exchangePortalMapper.mapToExchangePortalDto(analyzerService.findMinRatio(Currency.XMR))).thenReturn(exchangePortalDto);

        //When & Then
        mockMvc.perform(get("/v1/analyzer/old").contentType(MediaType.APPLICATION_JSON)
                .param("currency", "XMR")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.provider", is("nomics")))
                .andExpect(jsonPath("$.currencyToBuy", is("XMR")))
                .andExpect(jsonPath("$.currencyToPay", is("USD")))
                .andExpect(jsonPath("$.ratio", is(160.0)))
                .andExpect(jsonPath("$.time", is("2020-12-20T20:50:00")));
    }

    @Test
    public void getNewestValueTest() throws Exception {
        //Given
        ExchangePortalDto exchangePortalDto = new ExchangePortalDto(1L, "nomics", Currency.XMR,
                Currency.USD,160.00, LocalDateTime.of(2020, 12, 20, 20, 50));

        when(exchangePortalMapper.mapToExchangePortalDto(analyzerService.findMinRatio(Currency.XMR))).thenReturn(exchangePortalDto);

        //When & Then
        mockMvc.perform(get("/v1/analyzer/young").contentType(MediaType.APPLICATION_JSON)
                .param("currency", "XMR")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.provider", is("nomics")))
                .andExpect(jsonPath("$.currencyToBuy", is("XMR")))
                .andExpect(jsonPath("$.currencyToPay", is("USD")))
                .andExpect(jsonPath("$.ratio", is(160.0)))
                .andExpect(jsonPath("$.time", is("2020-12-20T20:50:00")));
    }
}

