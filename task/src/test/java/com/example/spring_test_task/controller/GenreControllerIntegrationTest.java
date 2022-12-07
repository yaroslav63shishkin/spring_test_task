package com.example.spring_test_task.controller;

import com.example.spring_test_task.dto.GenreDto;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application_test.properties")
@Sql(value = "/init_db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/drop_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class GenreControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Value("${url.api.version}")
    private String pathVersion;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void createBookTest() throws Exception {

        GenreDto genre = new GenreDto();
        genre.setDescription("Фантастика");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/spring_test_task/" + pathVersion + "/genres/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(genre)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value(genre.getDescription()));
    }

    @Test
    void getGenreByIdTest() throws Exception {

        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/spring_test_task/" +
                                pathVersion + "/genres/get/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.description").value("Фантастика"));
    }

    @Test
    void updateGenreTest() throws Exception {

        GenreDto genre = new GenreDto();
        genre.setId(1L);
        genre.setDescription("Детектив");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/spring_test_task/" +
                                pathVersion + "/genres/rewrite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(genre)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateFieldsTest() throws Exception {

        GenreDto genre = new GenreDto();
        genre.setId(1L);
        genre.setDescription("Детектив");

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("http://localhost:8080/spring_test_task/" +
                                pathVersion + "/genres/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(genre)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteGenreByIdTest() throws Exception {

        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/spring_test_task/" +
                                pathVersion + "/genres/delete/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void noElementExceptionTest() throws Exception {

        GenreDto genre = new GenreDto();
        genre.setId(150L);
        genre.setDescription("Фантастика");

        mockMvc.perform(MockMvcRequestBuilders.
                        patch("http://localhost:8080/spring_test_task/" + pathVersion + "/genres/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(genre)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoElementException));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/spring_test_task/" +
                                pathVersion + "/genres/get/{id}", genre.getId()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoElementException))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/spring_test_task/"
                                + pathVersion + "/genres/delete/{id}", genre.getId()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoElementException))
                .andExpect(status().isNotFound());
    }
}