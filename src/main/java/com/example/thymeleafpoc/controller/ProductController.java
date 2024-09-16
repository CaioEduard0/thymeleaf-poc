package com.example.thymeleafpoc.controller;

import com.example.thymeleafpoc.dto.ProductDTO;
import com.example.thymeleafpoc.mapper.ProductMapper;
import com.example.thymeleafpoc.model.Product;
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

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private static final String REDIRECT_PRODUCTS = "redirect:/products";

    private final ProductService productService;

    @GetMapping
    public String findAll(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String search) {
        Page<Product> products = productService.findAll(page, search);
        model.addAttribute("products", products);
        model.addAttribute("productDTO", new ProductDTO());
        return "product/list";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        return "product/create";
    }

    @PostMapping
    public String save(@ModelAttribute @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "product/create";
        }
        productService.save(productDTO);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/update/{id}")
    public String updatePage(Model model, @PathVariable Long id) {
        Product product = productService.find(id);
        model.addAttribute("id", id);
        model.addAttribute("productDTO", ProductMapper.toProductDTO(product));
        return "product/update";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "product/update";
        }
        productService.update(id, productDTO);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return REDIRECT_PRODUCTS;
    }
}
