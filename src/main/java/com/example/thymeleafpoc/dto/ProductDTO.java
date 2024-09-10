package com.example.thymeleafpoc.dto;

import com.example.thymeleafpoc.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotBlank(message = "Preencha o nome.")
    private String name;
    @NotNull(message = "Preencha a categoria.")
    private Category category;
    @NotBlank(message = "Preencha a descrição.")
    private String description;
    @NotNull(message = "Preencha o preço.")
    private BigDecimal price;
}
