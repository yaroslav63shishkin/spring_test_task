package com.example.spring_test_task.util;

import com.example.spring_test_task.model.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static com.example.spring_test_task.util.CreatorCopiesUtil.copyProperties;

import static org.junit.jupiter.api.Assertions.*;

class CreatorCopiesUtilTest {

    @Test
    void copyPropertiesTest() {

        Author author1 = Author.builder()
                               .id(1L)
                               .name("Андрей")
                               .middleName("Андреевич")
                               .build();

        Author author2 = Author.builder()
                               .id(2L)
                               .name("Максим")
                               .dateOfBirth(LocalDate.of(2002, 12, 7))
                               .build();

        Author authorResult = Author.builder()
                .id(1L)
                .name("Андрей")
                .middleName("Андреевич")
                .dateOfBirth(LocalDate.of(2002, 12, 7))
                .build();

        copyProperties(author1, author2);

        assertEquals(author2.getDateOfBirth(), authorResult.getDateOfBirth());
        assertEquals(author2.getName(), authorResult.getName());
    }
}