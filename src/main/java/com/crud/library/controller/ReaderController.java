package com.crud.library.controller;

import com.crud.library.domain.ReaderDto;
import com.crud.library.mapper.ReaderMapper;
import com.crud.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library")
public class ReaderController {
    @Autowired
    private ReaderService service;
    @Autowired
    private ReaderMapper mapper;

    @RequestMapping(method = RequestMethod.POST, value = "/addReader", consumes = APPLICATION_JSON_VALUE)
    public void addReader(@RequestBody ReaderDto readerDto) {
        service.addReader(mapper.mapToReader(readerDto));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllReaders")
    public List<ReaderDto> getReaders() {
        return mapper.mapToReaderDtoList(service.getAllReaders());
    }

    @RequestMapping(method = RequestMethod.GET ,value = "/findReader/{id}")
    public ReaderDto findReaderById(@PathVariable Long id) throws ReaderNotFoundException {
        return mapper.mapToReaderDto(service.fetchReaderById(id).orElseThrow(ReaderNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteReader/{id}")
    public void deleteReader(@PathVariable final Long id) {
        service.deleteById(id);
    }
}
