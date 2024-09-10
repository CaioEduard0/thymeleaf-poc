package com.example.thymeleafpoc.enums;

import lombok.Getter;

@Getter
public enum Category {

    ELECTRONICS ("Eletrônicos"),
    CLOTHES ("Roupas"),
    FURNITURE ("Móveis");

    private final String name;

    Category(String name) {
        this.name = name;
    }
}
