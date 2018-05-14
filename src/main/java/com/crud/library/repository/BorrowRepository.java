package com.crud.library.repository;

import com.crud.library.domain.BorrowedItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends CrudRepository<BorrowedItem, Long> {
    @Override
    BorrowedItem save(BorrowedItem borrowedItem);

    @Override
    List<BorrowedItem> findAll();

    Optional<BorrowedItem> findByReaderId(Long id);

    Optional<BorrowedItem> findByItemId(Long id);

    @Override
    void deleteById(Long id);
}
