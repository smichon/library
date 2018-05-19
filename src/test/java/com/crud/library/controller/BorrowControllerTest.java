package com.crud.library.controller;

import com.crud.library.config.LocalDateAdapter;
import com.crud.library.domain.BorrowedItem;
import com.crud.library.domain.BorrowedItemDto;
import com.crud.library.mapper.BorrowsMapper;
import com.crud.library.service.BorrowService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BorrowController.class)
public class BorrowControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BorrowService service;
    @MockBean
    private BorrowsMapper mapper;

    @Test
    public void shouldBorrowItem() throws Exception {
        //given
        BorrowedItem item = new BorrowedItem(1L, 2L, 3L,
                LocalDate.of(2018,04,24), LocalDate.of(2018, 05, 24));
        BorrowedItemDto itemDto = new BorrowedItemDto(1L, 2L, 3L,
                LocalDate.of(2018,04,24), LocalDate.of(2018, 05, 24));
        when(service.newItemToBorrowed(any(BorrowedItem.class))).thenReturn(item);
        when(mapper.mapToBorrowedItem(itemDto)).thenReturn(item);
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String jsonContent = gson.toJson(item);
        //when & then
        mockMvc.perform(post("/v1/library/borrowItem").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldFetchAllBorrows() throws Exception {
        //given
        List<BorrowedItemDto> borrowedItemDtoList = new ArrayList<>();
        borrowedItemDtoList.add(new BorrowedItemDto(1L, 2L, 3L,
                LocalDate.of(2018,04,24), LocalDate.of(2018, 05, 24)));
        when(mapper.mapToBorrowedItemsDtoList(service.getAllBorrowedItems())).thenReturn(borrowedItemDtoList);
        //whn & then
        mockMvc.perform(get("/v1/library/getAllBorrowedItems")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].borrowId", is(1)))
                .andExpect(jsonPath("$[0].itemId", is(2)))
                .andExpect(jsonPath("$[0].readerId", is(3)))
                .andExpect(jsonPath("$[0].borrowDate", is("2018-04-24")))
                .andExpect(jsonPath("$[0].returnDate", is("2018-05-24")));
    }
    @Test
    public void shouldDeleteBorrow() throws Exception {
        //given
        BorrowedItem item = new BorrowedItem(1L, 2L, 3L,
                LocalDate.of(2018,04,24), LocalDate.of(2018, 05, 24));
        when(service.findInBorrowedByItemId(2L)).thenReturn(Optional.of(item));
        //when & then
        mockMvc.perform(delete("/v1/library/deleteFromBorrowed?id=1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200));
    }
    @Test
    public void shouldFindBorrowByReaderId() throws Exception {
        //given
        Long readerId = 3L;
        BorrowedItem item = new BorrowedItem(1L, 2L, readerId,
                LocalDate.of(2018,04,24), LocalDate.of(2018, 05, 24));
        BorrowedItemDto itemDto = new BorrowedItemDto(1L, 2L, readerId,
                LocalDate.of(2018,04,24), LocalDate.of(2018, 05, 24));
        when(mapper.maptoBorrowedItemDto(any(BorrowedItem.class))).thenReturn(itemDto);
        when(service.findInBorrowedByReaderId(readerId)).thenReturn(Optional.of(item));
        //when & then
        mockMvc.perform(get("/v1/library/getBorrowedByReaderId/3").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8"))
                .andExpect(jsonPath("$.borrowId", is(1)))
                .andExpect(jsonPath("$.itemId", is(2)))
                .andExpect(jsonPath("$.readerId", is(3)))
                .andExpect(jsonPath("$.borrowDate", is("2018-04-24")))
                .andExpect(jsonPath("$.returnDate", is("2018-05-24")));
    }
    @Test
    public void shouldFindBorrowByItemId() throws Exception {
        //given
        BorrowedItem item = new BorrowedItem(1L, 2L, 3L,
                LocalDate.of(2018,04,24), LocalDate.of(2018, 05, 24));
        BorrowedItemDto itemDto = new BorrowedItemDto(1L, 2L, 3L,
                LocalDate.of(2018,04,24), LocalDate.of(2018, 05, 24));
        when(mapper.maptoBorrowedItemDto(any(BorrowedItem.class))).thenReturn(itemDto);
        when(service.findInBorrowedByItemId(2L)).thenReturn(Optional.of(item));
        //when & then
        mockMvc.perform(get("/v1/library/getBorrowedByItemId/2").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(jsonPath("$.borrowId", is(1)))
                .andExpect(jsonPath("$.itemId", is(2)))
                .andExpect(jsonPath("$.readerId", is(3)))
                .andExpect(jsonPath("$.borrowDate", is("2018-04-24")))
                .andExpect(jsonPath("$.returnDate", is("2018-05-24")));
    }
}