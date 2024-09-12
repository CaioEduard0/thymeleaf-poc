package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.dto.ProductDTO;
import com.example.thymeleafpoc.model.Product;
import com.example.thymeleafpoc.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> findAll(int page, ProductDTO productDTO) {
        return productRepository.findAll(PageRequest.of(page, 10));
    }

    public Product find(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void save(ProductDTO productDTO) {
        productRepository.save(new Product(null, productDTO.getName(), productDTO.getCategory(), productDTO.getDescription(), productDTO.getPrice(), true));
    }

    public void update(Long id, ProductDTO productDTO) {
        Product product = find(id);
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
