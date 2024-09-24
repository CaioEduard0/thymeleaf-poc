package com.example.thymeleafpoc.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

    @Setter(AccessLevel.NONE)
    private final List<Long> productsIds = new ArrayList<>();
}
