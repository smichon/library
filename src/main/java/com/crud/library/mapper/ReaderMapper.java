package com.crud.library.mapper;

import com.crud.library.domain.Reader;
import com.crud.library.domain.ReaderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderMapper {
    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(
                readerDto.getReaderId(),
                readerDto.getFirstname(),
                readerDto.getLastname(),
                readerDto.getAcountDate());
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getReaderId(),
                reader.getFirstname(),
                reader.getLastname(),
                reader.getAccountDate());
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readersList) {
        return readersList.stream()
                .map(reader -> new ReaderDto(
                        reader.getReaderId(), reader.getFirstname(), reader.getLastname(), reader.getAccountDate()))
                .collect(Collectors.toList());
    }

    public List<Reader> mapToReaderList(final List<ReaderDto> readerDtoList) {
        return readerDtoList.stream()
                .map(readerDto -> new Reader(
                        readerDto.getReaderId(), readerDto.getFirstname(), readerDto.getLastname(), readerDto.getAcountDate()))
                .collect(Collectors.toList());
    }
}
