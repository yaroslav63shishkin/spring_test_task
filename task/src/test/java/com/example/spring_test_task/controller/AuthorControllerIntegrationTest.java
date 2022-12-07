package com.example.spring_test_task.controller;

import com.example.spring_test_task.dto.AuthorDto;
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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import static com.example.spring_test_task.util.TranslatorJson.asJsonString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application_test.properties")
@Sql(value = "/init_db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/drop_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AuthorControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Value("${url.api.version}")
    private String pathVersion;

    private AuthorDto author;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        author = new AuthorDto();
        author.setName("Алексей");
        author.setSurname("Иванов");
        author.setMiddleName("Иванович");
    }

    @Test
    void createAuthorTest() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/spring_test_task/" + pathVersion + "/authors/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(author)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(author.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname")
                        .value(author.getSurname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.middleName")
                        .value(author.getMiddleName()));
    }

    @Test
    void getAuthorByIdTest() throws Exception{

        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/spring_test_task/" + pathVersion + "/authors/get/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.name").value("Василий"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.surname").value("Васильев"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.middleName").value("Васильевич"));
    }

    @Test
    void updateFieldsTest() throws Exception{

        author.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("http://localhost:8080/spring_test_task/" + pathVersion + "/authors/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(author)))
                .andExpect(status().isNoContent());
    }

    @Test
    void noEmptyStringAnnotationTest() throws Exception {

        author.setId(1L);
        author.setMiddleName("");

        mockMvc.perform(MockMvcRequestBuilders.
                        patch("http://localhost:8080/spring_test_task/" + pathVersion + "/authors/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(author)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException));
    }

    @Test
    void deleteAuthorByIdTest() throws Exception {

        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                .delete("http://localhost:8080/spring_test_task/"
                        + pathVersion + "/authors/delete/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void noElementExceptionTest() throws Exception {

        author.setId(150L);

        mockMvc.perform(MockMvcRequestBuilders.
                        patch("http://localhost:8080/spring_test_task/" + pathVersion + "/authors/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(author)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoElementException));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/spring_test_task/" +
                                pathVersion + "/authors/get/{id}", author.getId()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoElementException))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/spring_test_task/"
                                + pathVersion + "/authors/delete/{id}", author.getId()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoElementException))
                .andExpect(status().isNotFound());
    }
}