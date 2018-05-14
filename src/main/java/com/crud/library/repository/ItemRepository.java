package com.crud.library.repository;

import com.crud.library.domain.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {
    @Override
    Item save(Item reader);

    @Override
    List<Item> findAll();

    List<Item> findByTitleIdAndStatus(Long titleId, Item.Type status);

    @Override
    Optional<Item> findById(Long id);

    Long countItemsByTitleId(Long titleId);

    @Override
    void deleteById(Long id);
}
