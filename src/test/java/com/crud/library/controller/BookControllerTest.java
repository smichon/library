package com.crud.library.controller;

import com.crud.library.domain.Item;
import com.crud.library.domain.ItemDto;
import com.crud.library.domain.Title;
import com.crud.library.domain.TitleDto;
import com.crud.library.mapper.BookMapper;
import com.crud.library.service.BookService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.crud.library.domain.Item.Type.BORROWED;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService service;
    @MockBean
    private BookMapper mapper;

    @Test
    public void shouldAddTitle() throws Exception {
        //given
        Title title = new Title(1L, "Test author", "Test title", 1997);
        TitleDto titleDto = new TitleDto(1L, "Test author", "Test title", 1997);

        when(service.addTitle(any(Title.class))).thenReturn(title);
        when(mapper.mapToTitle(titleDto)).thenReturn(title);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(titleDto);

        //when & then
        mockMvc.perform(post("/v1/library/addTitle")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldGetAllTitles() throws Exception {
        //given
        Long titleId = 2L;
        List<TitleDto> titleDtoList = new ArrayList<>();
        titleDtoList.add(new TitleDto(titleId, "Test author", "Test title", 1997));
        when(mapper.mapToTitleDtoList(service.getAllTitles())).thenReturn(titleDtoList);
        //when&then
        mockMvc.perform(get("/v1/library/getAllTitles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titleId", is(2)))
                .andExpect(jsonPath("$[0].author", is("Test author")))
                .andExpect(jsonPath("$[0].title", is("Test title")))
                .andExpect(jsonPath("$[0].publicationYear", is(1997)));
    }

    @Test
    public void shouldGetOneTitle() throws Exception {
        //given
        Long titleId = 2L;
        Title title = new Title(titleId, "Test author", "Test title", 1997);
        TitleDto titleDto = new TitleDto(titleId, "Test author", "Test title", 1997);
        when(mapper.mapToTitleDto(any(Title.class))).thenReturn(titleDto);
        when(service.findTitleById(titleId)).thenReturn(Optional.of(title));
        //when & then
        mockMvc.perform(get("/v1/library/getTitle/2").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8"))
                .andExpect(jsonPath("$.titleId",is(2)))
                .andExpect(jsonPath("$.author", is("Test author")))
                .andExpect(jsonPath("$.title", is("Test title")))
                .andExpect(jsonPath("$.publicationYear", is(1997)));

    }

    @Test
    public void shouldDeleteTitle() throws Exception {
        //given
        Title title = new Title(2L, "Test author", "Test title", 1997);
        when(service.findTitleById(any(Long.class))).thenReturn(Optional.of(title));
        //when & then
        mockMvc.perform(delete("/v1/library/deleteTitle?id=2").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8"))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldAddItem() throws Exception {
        //given
        Item item = new Item(1L, 2L, BORROWED);
        ItemDto itemDto = new ItemDto(1L, 2L, BORROWED);
        when(service.addItem(any(Item.class))).thenReturn(item);
        when(mapper.mapToItem(itemDto)).thenReturn(item);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(itemDto);

        //when & then
        mockMvc.perform(post("/v1/library/addItem").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldGetAllItems() throws Exception {
        //given
        List<ItemDto> itemDtoList = new ArrayList<>();
        itemDtoList.add(new ItemDto(1L, 2L, BORROWED));
        when(mapper.mapToItemDtoList(service.getAllItems())).thenReturn(itemDtoList);
        //when&then
        mockMvc.perform(get("/v1/library/getAllItems").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].itemId", is(1)))
                .andExpect(jsonPath("$[0].titleId", is(2)))
                .andExpect(jsonPath("$[0].status", is("BORROWED")));
    }

    @Test
    public void shouldGetItem() throws Exception {
        //given
        Long itemId = 1L;
        Item item = new Item(itemId, 2L, BORROWED);
        ItemDto itemDto = new ItemDto(itemId, 2L, BORROWED);

        when(mapper.mapToItemDto(any(Item.class))).thenReturn(itemDto);
        when(service.findItem(itemId)).thenReturn(Optional.of(item));
        //when & then
        mockMvc.perform(get("/v1/library/getItem/1").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8"))
                .andExpect(jsonPath("$.itemId", is(1)))
                .andExpect(jsonPath("$.titleId", is(2)))
                .andExpect(jsonPath("$.status", is("BORROWED")));
    }

    @Test
    public void shouldDeleteItem() throws Exception {
        //given
        Item item = new Item(1L, 2L, BORROWED);
        when(service.findItem(any(Long.class))).thenReturn(Optional.of(item));
        //when & then
        mockMvc.perform(delete("/v1/library/deleteItem?itemId=1").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8"))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldGetAvailableTitle() throws Exception {
        //given
        Long titleId = 2L;
        List<ItemDto> itemDtoList = new ArrayList<>();
        itemDtoList.add(new ItemDto(1L, titleId, BORROWED));
        when(mapper.mapToItemDtoList(service.itemsToBorrowQTy(titleId, BORROWED))).thenReturn(itemDtoList);
        //when & then
        mockMvc.perform(get("/v1/library/availableTitle/2/BORROWED").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].itemId", is(1)))
                .andExpect(jsonPath("$[0].titleId", is(2)))
                .andExpect(jsonPath("$[0].status", is("BORROWED")));
    }

    @Test
    public void shouldCountItemsOfTitle() throws Exception {
        //given
        ItemDto itemDto = new ItemDto(1L, 2L, BORROWED);
        when(service.itemsOfTitleQty(1L)).thenReturn(1L);
        //when & then
        mockMvc.perform(get("/v1/library/countItemsOfTitle/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldUpdateItem() throws Exception {
        //given
        Item item = new Item(1L, 2L, BORROWED);
        ItemDto itemDto = new ItemDto(1L, 2L, BORROWED);

        when(mapper.mapToItemDto(any(Item.class))).thenReturn(itemDto);
        when(service.addItem(any(Item.class))).thenReturn(item);
        when(mapper.mapToItem(any(ItemDto.class))).thenReturn(item);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(itemDto);
        //when & then
        mockMvc.perform(put("/v1/library/updateItem").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.itemId", is(1)))
                .andExpect(jsonPath("$.titleId", is(2)))
                .andExpect(jsonPath("$.status", is("BORROWED")));
    }
}