package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "borrowedItems")
public class BorrowedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long borrowId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "reader_id")
    private Long readerId;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;
}
