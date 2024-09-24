package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.dto.OrderDTO;
import com.example.thymeleafpoc.model.Customer;
import com.example.thymeleafpoc.model.Order;
import com.example.thymeleafpoc.model.Product;
import com.example.thymeleafpoc.model.User;
import com.example.thymeleafpoc.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public Page<Order> findAll(int page, User user) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Optional<? extends GrantedAuthority> grantedAuthority = user.getAuthorities().stream().findFirst();
        if (grantedAuthority.isPresent() && grantedAuthority.get().getAuthority().equals("USER")) {
            return orderRepository.findByCustomerId(user.getId(), pageRequest);
        }
        return orderRepository.findAll(pageRequest);
    }

    public Order find(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void save(OrderDTO orderDTO, User user) {
        Customer customer = customerService.find(user.getId());
        List<Product> products = productService.findAll(orderDTO.getProductsIds());
        BigDecimal total = products.stream().map(Product::getPrice)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderRepository.save(Order.builder()
                                  .total(total)
                                  .customer(customer)
                                  .products(new HashSet<>(products))
                                  .build());
    }
}
