package com.example.thymeleafpoc.controller;

import com.example.thymeleafpoc.dto.OrderDTO;
import com.example.thymeleafpoc.dto.ProductDTO;
import com.example.thymeleafpoc.mapper.ProductMapper;
import com.example.thymeleafpoc.model.Order;
import com.example.thymeleafpoc.model.Product;
import com.example.thymeleafpoc.service.OrderService;
import com.example.thymeleafpoc.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final List<Long> cart = new ArrayList<>();
    private static List<Product> products;

    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/add-to-cart")
    public String addToCart(Model model, @RequestParam Long id, @ModelAttribute ProductDTO productDTO) {
        cart.add(id);
        Page<Product> products = productService.findAll(0, productDTO);
        model.addAttribute("products", products);
        model.addAttribute("productDTO", productDTO);
        return "product/list";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        products = productService.findAll(cart);
        model.addAttribute("products", products);
        return "order/create";
    }

    @PostMapping
    public String save() {
        orderService.save(products);
        cart.clear();
        products.clear();
        return "redirect:/products";
    }
}
