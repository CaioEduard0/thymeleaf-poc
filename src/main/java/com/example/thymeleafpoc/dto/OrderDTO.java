package com.example.thymeleafpoc.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long customerId;
    @Setter(AccessLevel.NONE)
    private final List<Long> productsIds = new ArrayList<>();
}
