package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.dto.ProductDTO;
import com.example.thymeleafpoc.enums.Category;
import com.example.thymeleafpoc.model.Product;
import com.example.thymeleafpoc.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<Product> findAll(int page, String search) {
        Pageable pageable = PageRequest.of(page, 10);
        if (search == null || search.isEmpty()) {
            return productRepository.findAll(pageable);
        }
        return productRepository.findAll(search, pageable);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public Product find(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(ProductDTO productDTO) {
        productRepository.save(Product.builder()
                         .name(productDTO.getName())
                         .category(productDTO.getCategory())
                         .description(productDTO.getDescription())
                         .price(productDTO.getPrice())
                         .active(true)
                         .build());
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

    @Transactional(readOnly = true)
    public Map<String, Integer> getPricesByRange() {
        List<BigDecimal> prices = productRepository.getPrices();
        Map<String, Integer> pricesByRange = new LinkedHashMap<>();
        for (BigDecimal price : prices) {
            if (price.compareTo(BigDecimal.ZERO) > 0 && price.compareTo(BigDecimal.valueOf(100)) < 0) {
                pricesByRange.put("0.1 - 100", pricesByRange.getOrDefault("0.1 - 100", 0) + 1);
            } else if (price.compareTo(BigDecimal.valueOf(100)) >= 0 && price.compareTo(BigDecimal.valueOf(500)) < 0) {
                pricesByRange.put("100 - 500", pricesByRange.getOrDefault("100 - 500", 0) + 1);
            } else if (price.compareTo(BigDecimal.valueOf(500)) >= 0 && price.compareTo(BigDecimal.valueOf(1000)) < 0) {
                pricesByRange.put("500 - 1000", pricesByRange.getOrDefault("500 - 1000", 0) + 1);
            } else if (price.compareTo(BigDecimal.valueOf(1000)) >= 0 && price.compareTo(BigDecimal.valueOf(5000)) < 0) {
                pricesByRange.put("1000 - 5000", pricesByRange.getOrDefault("1000 - 5000", 0) + 1);
            } else {
                pricesByRange.put("5000 +", pricesByRange.getOrDefault("5000 +", 0) + 1);
            }
        }
        return pricesByRange;
    }

    @Transactional(readOnly = true)
    public Map<String, Integer> getCategoriesQuantity() {
        List<Category> categories = productRepository.getCategories();
        Map<String, Integer> categoriesQuantity = new LinkedHashMap<>();
        for (Category category: categories) {
            categoriesQuantity.put(category.getName(), categoriesQuantity.getOrDefault(category.getName(), 0) + 1);
        }
        return categoriesQuantity;
    }
}
