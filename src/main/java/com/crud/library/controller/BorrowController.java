package com.crud.library.controller;

import com.crud.library.domain.BorrowedItemDto;
import com.crud.library.mapper.BorrowsMapper;
import com.crud.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library")
public class BorrowController {
    @Autowired
    private BorrowsMapper mapper;
    @Autowired
    private BorrowService service;

    @RequestMapping(method = RequestMethod.POST, value = "/borrowItem", consumes = APPLICATION_JSON_VALUE)
    public void borrowItem(@RequestBody BorrowedItemDto borrowedItemDto) {
        service.newItemToBorrowed(mapper.mapToBorrowedItem(borrowedItemDto));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllBorrowedItems")
    public List<BorrowedItemDto> getAllBorrowedItems() {
        return mapper.mapToBorrowedItemsDtoList(service.getAllBorrowedItems());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteFromBorrowed")
    public void deleteFromBorrowed(@RequestParam Long id) {
        service.deleteFromBorrowed(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBorrowedByReaderId/{id}")
    public BorrowedItemDto getBorrowedByReaderId(@PathVariable Long id) throws ReaderNotFoundException {
        return mapper.maptoBorrowedItemDto(service.findInBorrowedByReaderId(id).orElseThrow(ReaderNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBorrowedByItemId/{id}")
    public BorrowedItemDto getBorrowedByItemId(@PathVariable Long id) throws ReaderNotFoundException {
        return mapper.maptoBorrowedItemDto(service.findInBorrowedByItemId(id).orElseThrow(ReaderNotFoundException::new));
    }
}
