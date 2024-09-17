package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.dto.OrderDTO;
import com.example.thymeleafpoc.model.Customer;
import com.example.thymeleafpoc.model.Order;
import com.example.thymeleafpoc.model.Product;
import com.example.thymeleafpoc.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public Page<Order> findAll(int page) {
        return orderRepository.findAll(PageRequest.of(page, 10));
    }

    public void save(OrderDTO orderDTO) {
        Order order = new Order();
        Customer customer = customerService.findById(orderDTO.getCustomerId());
        order.setCustomer(customer);
        List<Product> products = productService.findAll(orderDTO.getProductsIds());
        BigDecimal total = products.stream().map(Product::getPrice)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        order.setProducts(new HashSet<>(products));
        orderRepository.save(order);
    }
}
