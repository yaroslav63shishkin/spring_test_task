package com.example.spring_test_task.controller;

import com.example.spring_test_task.dto.BookSlimDto;
import com.example.spring_test_task.exception.NoElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.example.spring_test_task.util.TranslatorJson.asJsonString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application_test.properties")
@Sql(value = "/init_db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/drop_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BookControllerIntegrationTest {


    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Value("${url.api.version}")
    private String pathVersion;

    private BookSlimDto book;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        book = new BookSlimDto();
        book.setIsbn("9781556156786");
    }

    @Test
    void createBookTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/spring_test_task/" + pathVersion + "/books/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn")
                        .value(book.getIsbn()));
    }

    @Test
    void getAuthorByIdTest() throws Exception {

        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/spring_test_task/" +
                                pathVersion + "/books/get/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.isbn").value("978-5-25841-135-1"));
    }

    @Test
    void updateBookTest() throws Exception {

        book.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders
                .put("http://localhost:8080/spring_test_task/" +
                        pathVersion + "/books/rewrite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(book)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateFieldsTest() throws Exception {

        book.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("http://localhost:8080/spring_test_task/" +
                                pathVersion + "/books/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBookByIdTest() throws Exception {

        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/spring_test_task/" +
                                pathVersion + "/books/delete/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void noElementExceptionTest() throws Exception {

        book.setId(150L);

        mockMvc.perform(MockMvcRequestBuilders.
                        patch("http://localhost:8080/spring_test_task/" + pathVersion + "/books/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoElementException));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/spring_test_task/" +
                                pathVersion + "/books/get/{id}", book.getId()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoElementException))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/spring_test_task/"
                                + pathVersion + "/books/delete/{id}", book.getId()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoElementException))
                .andExpect(status().isNotFound());
    }
}