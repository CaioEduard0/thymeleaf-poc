package com.example.thymeleafpoc.mapper;

import com.example.thymeleafpoc.dto.ProductDTO;
import com.example.thymeleafpoc.model.Product;

public class ProductMapper {

    public static ProductDTO toProductDTO(Product product) {
        return new ProductDTO(product.getName(), product.getCategory(), product.getDescription(), product.getPrice());
    }
}
