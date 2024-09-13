package com.example.thymeleafpoc.dto;

import com.example.thymeleafpoc.model.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @NotEmpty(message = "Selecione ao menos um produto.")
    private List<Product> products;
}
