package com.example.spring_test_task;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application_test.properties")
class SpringTestTaskApplicationTests {

    @Test
    void contextLoads() {
    }

}
