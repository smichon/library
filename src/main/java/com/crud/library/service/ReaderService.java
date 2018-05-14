package com.crud.library.service;

import com.crud.library.domain.Reader;
import com.crud.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository repository;

    public Reader addReader(Reader reader) {
        return repository.save(reader);
    }

    public List<Reader> getAllReaders() {
        return repository.findAll();
    }

    public Optional<Reader> fetchReaderById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
