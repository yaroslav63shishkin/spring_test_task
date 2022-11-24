package com.example.spring_test_task.mapper;

import com.example.spring_test_task.dto.BookDto;
import com.example.spring_test_task.dto.BookSlimDto;
import com.example.spring_test_task.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = GenreMapper.class)
public abstract class BookMapper {

    public abstract BookSlimDto toBookSlimDto(Book book);

    public abstract BookDto toBookDto(Book book);

    public abstract Book toBook(BookDto bookDto);

    public abstract Book toBook(BookSlimDto bookSlimDto);
}
