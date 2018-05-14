package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BorrowedItemDto {
    private Long borrowId;
    private Long itemId;
    private Long readerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
