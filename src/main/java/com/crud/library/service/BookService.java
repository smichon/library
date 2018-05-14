package com.crud.library.service;

import com.crud.library.domain.Item;
import com.crud.library.domain.Title;
import com.crud.library.repository.ItemRepository;
import com.crud.library.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TitleRepository titleRepository;


    public Title addTitle(final Title title) {
        return titleRepository.save(title);
    }

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public Optional<Title> findTitleById(final Long id) {
        return titleRepository.findById(id);
    }

    public void deleteTitle(final Long id) {
        titleRepository.deleteById(id);
    }


    public Item addItem(final Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void deleteItem(final Long id) {
        itemRepository.deleteById(id);
    }

    public Optional<Item> findItem(final Long id) {
        return itemRepository.findById(id);
    }

    public Long itemsOfTitleQty(final Long id) {
        return itemRepository.countItemsByTitleId(id);
    }

    public List<Item> itemsToBorrowQTy(final Long id, final Item.Type status ) {
        return itemRepository.findByTitleIdAndStatus(id, status);
    }
}
