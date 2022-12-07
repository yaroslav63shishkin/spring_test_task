package com.example.spring_test_task.mapper;

import com.example.spring_test_task.dto.AuthorAllDto;
import com.example.spring_test_task.dto.AuthorDto;
import com.example.spring_test_task.model.Author;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = BookMapper.class)
public abstract class AuthorMapper {

    public abstract AuthorDto toAuthorDto(Author author);

    public abstract AuthorAllDto toAuthorAllDto(Author author);

    public abstract Author toAuthor(AuthorDto authorDto);

    public abstract Author toAuthor(AuthorAllDto authorDto);

}
