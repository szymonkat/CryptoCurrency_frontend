package com.vaadin.controller;

import com.google.gson.Gson;
import com.vaadin.domain.Currency;
import com.vaadin.dto.ItemToBuyDto;
import com.vaadin.dto.WalletDto;
import com.vaadin.dto.WalletItemDto;
import com.vaadin.mapper.ItemToBuyMapper;
import com.vaadin.service.interfaces.ItemToBuyService;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemToBuyController.class)
public class ItemToBuyControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemToBuyService itemToBuyService;

    @MockBean
    private ItemToBuyMapper itemToBuyMapper;

    @Test
    public void getItemsToBuyTest() throws Exception {
        //Given
        List<ItemToBuyDto> itemToBuyDtoList = new ArrayList<>();
        ItemToBuyDto itemToBuyDto1 = new ItemToBuyDto(1L, 2L, 23.0);
        ItemToBuyDto itemToBuyDto2 = new ItemToBuyDto(3L, 4L, 45.0);
        itemToBuyDtoList.add(itemToBuyDto1);
        itemToBuyDtoList.add(itemToBuyDto2);
        when(itemToBuyMapper.mapToItemToBuyDtoList(itemToBuyService.getItemToBuys())).thenReturn(itemToBuyDtoList);
        //When & Then
        mockMvc.perform(get("/v1/items").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].exchangePortalId", is(2)))
                .andExpect(jsonPath("$[0].quantityToBuy", is(23.0)))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].exchangePortalId", is(4)))
                .andExpect(jsonPath("$[1].quantityToBuy", is(45.0)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getItemToBuyTest() throws Exception {
        //Given
        ItemToBuyDto itemToBuyDto2 = new ItemToBuyDto(3L, 4L, 45.0);

        when(itemToBuyMapper.mapToItemToBuyDto(itemToBuyService.findItemToBuyById(3L))).thenReturn(itemToBuyDto2);
        //When & Then
        mockMvc.perform(get("/v1/items/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.exchangePortalId", is(4)))
                .andExpect(jsonPath("$.quantityToBuy", is(45.0)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createItemToBuyTest() throws Exception {
        //Given
        ItemToBuyDto itemToBuyDto2 = new ItemToBuyDto(3L, 4L, 45.0);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(itemToBuyDto2);

        when(itemToBuyMapper.mapToItemToBuyDto(itemToBuyService.createItemToBuy(
                (itemToBuyMapper.mapToItemToBuy(itemToBuyDto2))))).thenReturn(itemToBuyDto2);

        //When & Then
        mockMvc.perform(post("/v1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateItemToBuyTest() throws Exception {
        //Given
        ItemToBuyDto itemToBuyDto2 = new ItemToBuyDto(3L, 4L, 45.0);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(itemToBuyDto2);

        when(itemToBuyMapper.mapToItemToBuyDto(itemToBuyService.updateItemToBuy
                (itemToBuyMapper.mapToItemToBuy(itemToBuyDto2)))).thenReturn(itemToBuyDto2);

        //When & Then
        mockMvc.perform(put("/v1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.exchangePortalId", is(4)))
                .andExpect(jsonPath("$.quantityToBuy", is(45.0)));
    }

    @Test
    void finalizeItemToBuyTest() throws Exception {
        //Given
        //When & Then
        mockMvc.perform(get("/v1/items/finalize/2")
                .contentType(MediaType.APPLICATION_JSON)
                .param("itemToBuyId", "4")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteItemToBuyTest() throws Exception {
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/items/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
