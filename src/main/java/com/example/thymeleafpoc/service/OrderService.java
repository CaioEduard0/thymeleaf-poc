package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.model.Order;
import com.example.thymeleafpoc.model.Product;
import com.example.thymeleafpoc.repository.CustomerRepository;
import com.example.thymeleafpoc.repository.OrderRepository;
import com.example.thymeleafpoc.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public Page<Order> findAll(int page) {
        return orderRepository.findAll(PageRequest.of(page, 10));
    }

    public Order find(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void save(List<Product> products) {
        Order order = new Order();
        order.setCustomer(customerRepository.findById(1L).get());
        BigDecimal total = products.stream().map(Product::getPrice)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        order.setProducts(new HashSet<>(products));
        orderRepository.save(order);
//        return orderRepository.save(order);
    }
}
