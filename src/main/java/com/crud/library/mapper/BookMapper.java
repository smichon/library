package com.crud.library.mapper;

import com.crud.library.domain.Item;
import com.crud.library.domain.ItemDto;
import com.crud.library.domain.Title;
import com.crud.library.domain.TitleDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(
                titleDto.getTitleId(),
                titleDto.getAuthor(),
                titleDto.getTitle(),
                titleDto.getPublicationYear());
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(
                title.getTitleId(),
                title.getAuthor(),
                title.getTitle(),
                title.getPublicationYear());
    }

    public Item mapToItem(final ItemDto itemDto) {
        return new Item(
                itemDto.getItemId(),
                itemDto.getTitleId(),
                itemDto.getStatus());
    }

    public ItemDto mapToItemDto(final Item item) {
        return new ItemDto(
                item.getItemId(),
                item.getTitleId(),
                item.getStatus());
    }

    public List<ItemDto> mapToItemDtoList(final List<Item> itemList) {
        return itemList.stream()
                .map(item -> new ItemDto(item.getItemId(), item.getTitleId(), item.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Item> mapToItemList(final List<ItemDto> itemDtoList) {
        return itemDtoList.stream()
                .map(itemDto -> new Item(itemDto.getItemId(), itemDto.getTitleId(), itemDto.getStatus()))
                .collect(Collectors.toList());
    }

    public List<TitleDto> mapToTitleDtoList(final List<Title> titleList) {
        return titleList.stream()
                .map(title -> new TitleDto(
                        title.getTitleId(), title.getAuthor(), title.getTitle(), title.getPublicationYear()))
                .collect(Collectors.toList());
    }

}
