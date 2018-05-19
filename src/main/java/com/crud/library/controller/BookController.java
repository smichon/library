package com.crud.library.controller;

import com.crud.library.domain.Item;
import com.crud.library.domain.ItemDto;
import com.crud.library.domain.TitleDto;
import com.crud.library.mapper.BookMapper;
import com.crud.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library")
public class BookController {
    @Autowired
    private BookService service;
    @Autowired
    private BookMapper mapper;

    @RequestMapping(method = RequestMethod.POST, value = "/addTitle", consumes = APPLICATION_JSON_VALUE)
    public void addTitle(@RequestBody TitleDto titleDto) {
        service.addTitle(mapper.mapToTitle(titleDto));
    }

    @RequestMapping(method = RequestMethod.GET,value = "/getAllTitles")
    public List<TitleDto> getAllTitles() {
        return mapper.mapToTitleDtoList(service.getAllTitles());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getTitle/{id}")
    public TitleDto getTitle(@PathVariable Long id) throws TitleNotFoundException {
        return mapper.mapToTitleDto(service.findTitleById(id).orElseThrow(TitleNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTitle")
    public void deleteTitle(@RequestParam final Long id) {
        service.deleteTitle(id);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/addItem", consumes = APPLICATION_JSON_VALUE)
    public void addItem(@RequestBody ItemDto itemDto) {
        service.addItem(mapper.mapToItem(itemDto));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllItems")
    public List<ItemDto> getAllItems() {
        return mapper.mapToItemDtoList(service.getAllItems());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteItem")
    public void deleteItem(@RequestParam Long itemId) {
        service.deleteItem(itemId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getItem/{id}")
    public ItemDto getItem(@PathVariable Long id) throws ItemNotFoundException {
        return mapper.mapToItemDto(service.findItem(id).orElseThrow(ItemNotFoundException::new));
    }


    @RequestMapping(method = RequestMethod.GET, value = "/availableTitle/{titleId}/{status}")
    public List<ItemDto> availableTitle(@PathVariable Long titleId, @PathVariable Item.Type status) {
        return mapper.mapToItemDtoList(service.itemsToBorrowQTy(titleId, status));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/countItemsOfTitle/{titleId}")
    public Long countItemsOfTitle(@PathVariable Long titleId) throws TitleNotFoundException {
        return service.itemsOfTitleQty(titleId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateItem")
    public ItemDto updateItem(@RequestBody ItemDto itemDto) {
        return mapper.mapToItemDto(service.addItem(mapper.mapToItem(itemDto)));
    }
}
