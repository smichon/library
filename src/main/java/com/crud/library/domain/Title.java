package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "titles")
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long titleId;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "publication_year")
    private int publicationYear;
}
