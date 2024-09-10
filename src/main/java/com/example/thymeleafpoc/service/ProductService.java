package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.dto.ProductDTO;
import com.example.thymeleafpoc.enums.Category;
import com.example.thymeleafpoc.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final List<Product> PRODUCTS = new ArrayList<>();
    private static Integer ID = 10;

    static {
        for (int i = 1; i <= 100; i++) {
            PRODUCTS.add(new Product(i, "Produto " + i, Category.ELECTRONICS, "Descrição do produto " + i, BigDecimal.valueOf(10000)));
        }
    }

    public List<Product> findAll() {
        return PRODUCTS;
    }

    public Page<Product> findAll(Pageable pageable) {
        long initialOffset = pageable.getOffset();
        long finalOffset = pageable.getOffset() + pageable.getPageSize();
        List<Product> content = PRODUCTS.stream().filter(p ->
                p.getId() >= initialOffset && p.getId() <= finalOffset).toList();

        return new PageImpl<>(content, pageable, PRODUCTS.size());

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

    public List<Product> find(String name) {
        return PRODUCTS.stream().filter(product -> product.getName().contains(name)).toList();
    }
}
