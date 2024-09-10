package com.example.thymeleafpoc.model;

import com.example.thymeleafpoc.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;
    private String name;
    private Category category;
    private String description;
    private BigDecimal price;
}
