package com.example.spring_test_task.dto.request;

import com.example.spring_test_task.util.annotation.NotEmptyString;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class PageRequestDto {

    @PositiveOrZero
    private Integer page = 0;

    @Positive
    private Integer size = 1;

    @NotEmptyString
    private String sortField = "id";
}
