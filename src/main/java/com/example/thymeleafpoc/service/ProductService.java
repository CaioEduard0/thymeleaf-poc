package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.dto.ProductDTO;
import com.example.thymeleafpoc.model.Product;
import com.example.thymeleafpoc.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> findAll(int page, String search) {
        Pageable pageable = PageRequest.of(page, 10);
        if (search == null || search.isEmpty()) {
            return productRepository.findAll(pageable);
        }
        return productRepository.findAll(search, pageable);
    }

    public List<Product> findAll(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

    public Product find(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(ProductDTO productDTO) {
        productRepository.save(new Product(null, productDTO.getName(), productDTO.getCategory(), productDTO.getDescription(), productDTO.getPrice(), true));
    }

    @Transactional
    public void update(Long id, ProductDTO productDTO) {
        Product product = find(id);
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.delete(id);
    }
}
