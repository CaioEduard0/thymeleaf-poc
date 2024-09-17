package com.example.thymeleafpoc.controller;

import com.example.thymeleafpoc.dto.OrderDTO;
import com.example.thymeleafpoc.dto.ProductDTO;
import com.example.thymeleafpoc.model.Customer;
import com.example.thymeleafpoc.model.Order;
import com.example.thymeleafpoc.model.Product;
import com.example.thymeleafpoc.service.CustomerService;
import com.example.thymeleafpoc.service.OrderService;
import com.example.thymeleafpoc.service.ProductService;
import com.example.thymeleafpoc.utils.PageUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final String ORDER_PRODUCT_PAGE = "order/product";

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping
    public String findAll(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Order> orders = orderService.findAll(page);
        model.addAttribute("orders", orders);
        PageUtils.formatPages(orders, model);
        return "order/list";
    }

    @GetMapping("/customers")
    public String selectCustomer(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Customer> customers = customerService.findAll(pageable, search);
        model.addAttribute("customers", customers);
        return "order/customer";
    }

    @GetMapping("/customers/{id}")
    public String fillInCustomer(Model model, @PathVariable Long id, HttpSession session) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(id);
        session.setAttribute("orderDTO", orderDTO);
        fillInModelWithProducts(model, 0, new ProductDTO());
        return ORDER_PRODUCT_PAGE;
    }

    @GetMapping("/products")
    public String selectProducts(Model model, @RequestParam(defaultValue = "0") int page, @ModelAttribute ProductDTO productDTO) {
        fillInModelWithProducts(model, page, productDTO);
        return ORDER_PRODUCT_PAGE;
    }

    @GetMapping("/products/add-to-cart")
    public String addToCart(Model model, @RequestParam Long id, @ModelAttribute ProductDTO productDTO, HttpSession session) {
        OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
        orderDTO.getProductsIds().add(id);
        fillInModelWithProducts(model, 0, productDTO);
        return ORDER_PRODUCT_PAGE;
    }

    @GetMapping("/products/remove-from-cart")
    public String removeFromCart(Model model, @RequestParam Long id, @ModelAttribute ProductDTO productDTO, HttpSession session) {
        OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
        orderDTO.getProductsIds().remove(id);
        fillInModelWithProducts(model, 0, productDTO);
        return ORDER_PRODUCT_PAGE;
    }

    private void fillInModelWithProducts(Model model, int page, ProductDTO productDTO) {
        Page<Product> products = productService.findAll(page, productDTO);
        model.addAttribute("products", products);
        model.addAttribute("productDTO", productDTO);
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
        List<Product> products = productService.findAll(orderDTO.getProductsIds());
        model.addAttribute("products", products);
        return "order/create";
    }

    @PostMapping
    public String save(HttpSession session) {
        OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
        orderService.save(orderDTO);
        session.removeAttribute("orderDTO");
        return "redirect:/orders";
    }
}
