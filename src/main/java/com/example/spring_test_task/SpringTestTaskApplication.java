package com.example.spring_test_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//fixme: добавить тесты
//fixme: вынести миграции как отдельный модуль, чтобы они не запускались при компиляции приложения
@SpringBootApplication
public class SpringTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTestTaskApplication.class, args);
    }

}
