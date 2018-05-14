package com.crud.library.repository;

import com.crud.library.domain.Title;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends CrudRepository<Title, Long> {
    @Override
    Title save(Title title);

    @Override
    List<Title> findAll();

    @Override
    Optional<Title> findById(Long id);

    @Override
    void deleteById(Long id);
}
