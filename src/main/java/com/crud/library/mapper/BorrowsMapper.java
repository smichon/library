package com.crud.library.mapper;

import com.crud.library.domain.BorrowedItem;
import com.crud.library.domain.BorrowedItemDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BorrowsMapper {
    public BorrowedItem mapToBorrowedItem(BorrowedItemDto borrowedItemDto) {
        return new BorrowedItem(
                borrowedItemDto.getBorrowId(),
                borrowedItemDto.getItemId(),
                borrowedItemDto.getReaderId(),
                borrowedItemDto.getBorrowDate(),
                borrowedItemDto.getReturnDate());
    }

    public BorrowedItemDto maptoBorrowedItemDto(BorrowedItem borrowedItem) {
        return new BorrowedItemDto(
                borrowedItem.getBorrowId(),
                borrowedItem.getItemId(),
                borrowedItem.getReaderId(),
                borrowedItem.getBorrowDate(),
                borrowedItem.getReturnDate());
    }

    public List<BorrowedItemDto> mapToBorrowedItemsDtoList(final List<BorrowedItem> borrowedItemList) {
        return borrowedItemList.stream()
                .map(borrowedItem -> new BorrowedItemDto(
                        borrowedItem.getBorrowId(), borrowedItem.getItemId(), borrowedItem.getReaderId(),
                        borrowedItem.getBorrowDate(),borrowedItem.getReturnDate()))
                .collect(Collectors.toList());
    }
}
