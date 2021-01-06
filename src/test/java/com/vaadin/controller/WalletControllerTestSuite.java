package com.vaadin.controller;

import com.google.gson.Gson;
import com.vaadin.domain.Currency;
import com.vaadin.dto.WalletDto;
import com.vaadin.dto.WalletItemDto;
import com.vaadin.mapper.WalletMapper;
import com.vaadin.service.interfaces.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WalletController.class)
public class WalletControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @MockBean
    private WalletMapper walletMapper;

    @Test
    public void getWalletsTest() throws Exception {
        //Given
        List<WalletDto> walletDtoList = new ArrayList<>();
        WalletDto walletDto1 = new WalletDto("wallet1");
        WalletDto walletDto2 = new WalletDto("wallet2");
        walletDtoList.add(walletDto1);
        walletDtoList.add(walletDto2);
        when(walletMapper.mapToWalletDtoList(walletService.getWallets())).thenReturn(walletDtoList);
        //When & Then
        mockMvc.perform(get("/v1/wallet").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("wallet1")))
                .andExpect(jsonPath("$[1].name", is("wallet2")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getWalletTest() throws Exception {
        //Given
        WalletDto walletDto1 = new WalletDto(1L, "wallet", null);
        Long walletId = 1L;

        when(walletMapper.mapToWalletDto(walletService.findWalletById(walletId))).thenReturn(walletDto1);
        //When & Then
        mockMvc.perform(get("/v1/wallet/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("wallet")))
                .andExpect(jsonPath("$.walletItemList", is(nullValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createWalletTest() throws Exception {
        //Given
        WalletDto walletDto1 = new WalletDto(1L, "wallet", null);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(walletDto1);

        when(walletMapper.mapToWalletDto(walletService.createWallet
                (walletMapper.mapToWallet(walletDto1)))).thenReturn(walletDto1);

        //When & Then
        mockMvc.perform(post("/v1/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateWalletTest() throws Exception {
        //Given
        WalletItemDto walletItemDto = new WalletItemDto(1L, Currency.BTC, 23.0);
        List<WalletItemDto> walletItemDtoList = new ArrayList<>();
        walletItemDtoList.add(walletItemDto);
        WalletDto walletDto1 = new WalletDto(1L, "wallet_after_update", walletItemDtoList);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(walletDto1);

        when(walletMapper.mapToWalletDto(walletService.updateWallet
                (walletMapper.mapToWallet(walletDto1)))).thenReturn(walletDto1);

        //When & Then
        mockMvc.perform(put("/v1/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("wallet_after_update")))
                .andExpect(jsonPath("$.walletItemList[0].walletId", is(1)))
                .andExpect(jsonPath("$.walletItemList[0].currency", is("BTC")))
                .andExpect(jsonPath("$.walletItemList[0].quantity", is(23.0)));
    }

    @Test
    public void deleteWalletTest() throws Exception {
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/wallet/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}

