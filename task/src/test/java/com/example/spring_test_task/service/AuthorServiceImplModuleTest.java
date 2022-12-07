package com.example.spring_test_task.service;

import com.example.spring_test_task.dto.AuthorDto;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.exception.NoElementException;
import com.example.spring_test_task.mapper.AuthorMapperImpl;
import com.example.spring_test_task.model.Author;
import com.example.spring_test_task.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.spring_test_task.util.CreatorCopiesUtil.copyProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthorServiceImplModuleTest {

    @Mock
    private AuthorRepository repository;

    @Mock
    private AuthorMapperImpl mapper;

    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    void createAuthor() {

        when(repository.save(any())).thenReturn(getAuthor());
        service.createAuthor(getAuthorDto());
        verify(repository,times(1)).save(any());
    }

    @Test
    void getAuthors() {

        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "id"));
        List<Author> authors = new ArrayList<>();
        authors.add(Author.builder().id(1L).build());
        authors.add(Author.builder().id(2L).build());
        authors.add(Author.builder().id(3L).build());

        long totalAuthors = 3L;

        Page<Author> pageResult = new PageImpl<>(authors, pageable, totalAuthors);

        when(repository.findAll(any(Pageable.class))).thenReturn(pageResult);

        Page<AuthorDto> page = service.getAuthors(new PageRequestDto());

        assertEquals(totalAuthors, page.getTotalElements());
        assertEquals(pageResult.getTotalPages(), page.getTotalPages());
    }

    @Test
    void getAuthorsByIdTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.getAuthorById(getAuthorDto().getId()));
    }

    @Test
    void rewriteAuthorTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.rewriteAuthor(getAuthorDto()));
    }

    @Test
    void updateAuthorTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.updateAuthor(getAuthorDto()));
    }

    @Test
    void removeAuthorByIdTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.removeAuthorById(getAuthor().getId()));
    }

    private Author getAuthor() {
        return Author.builder()
                .id(1L)
                .name("Алексей")
                .surname("Иванов")
                .middleName("Иванович")
                .dateOfBirth(LocalDate.now())
                .build();
    }

    private AuthorDto getAuthorDto() {

        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("Алексей");
        authorDto.setSurname("Иванов");
        authorDto.setMiddleName("Иванович");
        authorDto.setDateOfBirth(LocalDate.now());

        return authorDto;
    }
}