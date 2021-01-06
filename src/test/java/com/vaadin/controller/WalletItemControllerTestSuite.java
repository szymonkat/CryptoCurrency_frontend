package com.vaadin.controller;

import com.google.gson.Gson;
import com.vaadin.domain.Currency;
import com.vaadin.dto.WalletItemDto;
import com.vaadin.mapper.WalletItemMapper;
import com.vaadin.service.interfaces.WalletItemService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WalletItemController.class)
public class WalletItemControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletItemService walletItemService;

    @MockBean
    private WalletItemMapper walletItemMapper;

    @Test
    public void getWalletItemsTest() throws Exception {
        //Given
        List<WalletItemDto> walletItemDtoList = new ArrayList<>();
        WalletItemDto walletItemDto1 = new WalletItemDto(4L, 1L, Currency.BTC, 100.0);
        WalletItemDto walletItemDto2 = new WalletItemDto(5L, 2L, Currency.XMR, 1200.0);
        walletItemDtoList.add(walletItemDto1);
        walletItemDtoList.add(walletItemDto2);

        when(walletItemMapper.mapToWalletItemDtoList(walletItemService.getWalletItems())).thenReturn(walletItemDtoList);
        //When & Then
        mockMvc.perform(get("/v1/walletItem").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[0].walletId", is(1)))
                .andExpect(jsonPath("$[0].currency", is("BTC")))
                .andExpect(jsonPath("$[0].quantity", is(100.0)))
                .andExpect(jsonPath("$[1].id", is(5)))
                .andExpect(jsonPath("$[1].walletId", is(2)))
                .andExpect(jsonPath("$[1].currency", is("XMR")))
                .andExpect(jsonPath("$[1].quantity", is(1200.0)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getWalletItemTest() throws Exception {
        //Given
        WalletItemDto walletItemDto1 = new WalletItemDto(4L, 1L, Currency.BTC, 100.0);

        when(walletItemMapper.mapToWalletItemDto(walletItemService.findWalletItemById(4L))).thenReturn(walletItemDto1);
        //When & Then
        mockMvc.perform(get("/v1/walletItem/4").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.walletId", is(1)))
                .andExpect(jsonPath("$.currency", is("BTC")))
                .andExpect(jsonPath("$.quantity", is(100.0)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createWalletItemTest() throws Exception {
        //Given
        WalletItemDto walletItemDto1 = new WalletItemDto(4L, 1L, Currency.BTC, 100.0);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(walletItemDto1);

        when(walletItemMapper.mapToWalletItemDto(walletItemService.modifyWalletItem
                (walletItemMapper.mapToWalletItem(walletItemDto1)))).thenReturn(walletItemDto1);

        //When & Then
        mockMvc.perform(post("/v1/walletItem")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateWalletItemTest() throws Exception {
        //Given
        WalletItemDto walletItemDto1 = new WalletItemDto(4L, 1L, Currency.BTC, 100.0);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(walletItemDto1);

        when(walletItemMapper.mapToWalletItemDto(walletItemService.updateWalletItem
                (walletItemMapper.mapToWalletItem(walletItemDto1)))).thenReturn(walletItemDto1);

        //When & Then
        mockMvc.perform(put("/v1/walletItem")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.walletId", is(1)))
                .andExpect(jsonPath("$.currency", is("BTC")))
                .andExpect(jsonPath("$.quantity", is(100.0)));
    }

    @Test
    public void deleteWalletTest() throws Exception {
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/walletItem/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
