package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.dto.ProductDTO;
import com.example.thymeleafpoc.enums.Category;
import com.example.thymeleafpoc.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private static final List<Product> PRODUCTS = new ArrayList<>();
    private static Integer ID = 10;

    static {
        for (int i = 1; i <= 10; i++) {
            PRODUCTS.add(new Product(i, "Produto " + i, Category.ELECTRONICS, "Descrição do produto " + i, BigDecimal.valueOf(10000)));
        }
    }

    public List<Product> findAll() {
        return PRODUCTS;
    }

    public Product find(Integer id) {
        return PRODUCTS.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public void save(ProductDTO productDTO) {
        ID += 1;
        PRODUCTS.add(new Product(ID, productDTO.getName(), productDTO.getCategory(), productDTO.getDescription(), productDTO.getPrice()));
    }

    public void update(Integer id, ProductDTO productDTO) {
        Product product = find(id);
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
    }

    public void delete(Integer id) {
        Product product = find(id);
        PRODUCTS.remove(product);
    }
}
