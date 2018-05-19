package com.crud.library.controller;

import com.crud.library.config.LocalDateAdapter;
import com.crud.library.domain.Reader;
import com.crud.library.domain.ReaderDto;
import com.crud.library.mapper.ReaderMapper;
import com.crud.library.service.ReaderService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReaderController.class)
public class ReaderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReaderService service;
    @MockBean
    private ReaderMapper mapper;

    @Test
    public void shouldAddReader() throws Exception {
        //given
        Reader reader = new Reader(
                3L ,"Gerald", "Briggs", LocalDate.of(2018,4,17));
        ReaderDto readerDto = new ReaderDto(
                3L ,"Gerald", "Briggs", LocalDate.of(2018, 4, 17));
        when(service.addReader(any(Reader.class))).thenReturn(reader);
        when(mapper.mapToReader(readerDto)).thenReturn(reader);
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String jsonContent = gson.toJson(reader);
        /*String jsonContent = new String("{ \"firstname\": \"John\",\n" +
                "\"lastname\": \"Doe\",\n" +
                "\"accountDate\": \"2017-08-17\"}\n");*/

        //when & then
        mockMvc.perform(post("/v1/library/addReader").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldGetAllReaders() throws Exception {
        //given
        List<ReaderDto> readerDtoList = new ArrayList<>();
        readerDtoList.add(new ReaderDto(
                1L, "John", "Doe", LocalDate.of(2018, 02, 24)));
        when(mapper.mapToReaderDtoList(service.getAllReaders())).thenReturn(readerDtoList);

        //when & then
        mockMvc.perform(get("/v1/library/getAllReaders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].readerId",is(1)))
                .andExpect(jsonPath("$[0].firstname", is("John")))
                .andExpect(jsonPath("$[0].lastname", is("Doe")))
                .andExpect(jsonPath("$[0].accountDate", is("2018-02-24")));
    }

    @Test
    public void shouldGetReader() throws Exception {
        //given
        Long readerId = 1L;
        Reader reader = new Reader(
                readerId ,"Gerald", "Briggs", LocalDate.of(2018,4,17));
        ReaderDto readerDto = new ReaderDto(
                readerId ,"Gerald", "Briggs", LocalDate.of(2018, 4, 17));
        when(mapper.mapToReaderDto(any(Reader.class))).thenReturn(readerDto);
        when(service.fetchReaderById(readerId)).thenReturn(Optional.of(reader));
        //when & then
        mockMvc.perform(get("/v1/library/findReader/1").contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8"))
                .andExpect(jsonPath("$.readerId",is(1)))
                .andExpect(jsonPath("$.firstname", is("Gerald")))
                .andExpect(jsonPath("$.lastname", is("Briggs")))
                .andExpect(jsonPath("$.accountDate", is("2018-04-17")));
        }

    @Test
    public void shouldDeleteReader() throws Exception {
        //given
        Reader reader = new Reader(
                26L ,"Gerald", "Briggs", LocalDate.of(2018,4,17));
        when(service.fetchReaderById(any(Long.class))).thenReturn(Optional.of(reader));
        //when & then
        mockMvc.perform(delete("/v1/library/deleteReader/26")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200));
    }
}