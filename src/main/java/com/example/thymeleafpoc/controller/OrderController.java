package com.example.thymeleafpoc.controller;

import com.example.thymeleafpoc.dto.OrderDTO;
import com.example.thymeleafpoc.model.Order;
import com.example.thymeleafpoc.model.Product;
import com.example.thymeleafpoc.model.User;
import com.example.thymeleafpoc.service.OrderService;
import com.example.thymeleafpoc.service.ProductService;
import com.example.thymeleafpoc.utils.PageUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping
    public String findAll(Model model, @RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal User user) {
        Page<Order> orders = orderService.findAll(page, user);
        model.addAttribute("orders", orders);
        user.getAuthorities().stream().findFirst().ifPresent(authority -> model.addAttribute("authority", authority));
        PageUtils.format(orders, model);
        return "order/list";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session, @AuthenticationPrincipal User user) {
        if (user.getCustomer() == null) {
            return "redirect:/customers/create";
        }
        OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
        List<Product> products = productService.findAll(orderDTO.getProductsIds());
        BigDecimal total = calculateTotal(products);
        model.addAttribute("products", products);
        model.addAttribute("total", total);
        return "order/create";
    }

    @PostMapping
    public String save(HttpSession session, @AuthenticationPrincipal User user) {
        OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
        orderService.save(orderDTO, user);
        session.removeAttribute("orderDTO");
        return "redirect:/orders";
    }

    @GetMapping("{id}/products")
    public String getOrderProducts(Model model, @PathVariable Long id) {
        Order order = orderService.find(id);
        Set<Product> products = order.getProducts();
        BigDecimal total = calculateTotal(products);
        model.addAttribute("products", products);
        model.addAttribute("total", total);
        return "order/products";
    }

    private BigDecimal calculateTotal(Collection<Product> products) {
        return products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
