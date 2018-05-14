package com.crud.library.service;

import com.crud.library.domain.BorrowedItem;
import com.crud.library.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository repository;

    public BorrowedItem newItemToBorrowed(final BorrowedItem borrowedItem) {
        return repository.save(borrowedItem);
    }

    public List<BorrowedItem> getAllBorrowedItems() {
        return repository.findAll();
    }

    public void deleteFromBorrowed(final Long id) {
        repository.deleteById(id);
    }

    public Optional<BorrowedItem> findInBorrowedByReaderId(final Long id) {
        return repository.findByReaderId(id);
    }

    public Optional<BorrowedItem> findInBorrowedByItemId(final Long id) {
        return repository.findByItemId(id);
    }
}
