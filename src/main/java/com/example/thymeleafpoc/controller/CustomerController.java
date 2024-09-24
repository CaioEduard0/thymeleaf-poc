package com.example.thymeleafpoc.controller;

import com.example.thymeleafpoc.dto.CustomerDTO;
import com.example.thymeleafpoc.model.User;
import com.example.thymeleafpoc.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("customerDTO", new CustomerDTO());
        return "customer/create";
    }

    @PostMapping
    public String save(@ModelAttribute @Valid CustomerDTO customerDTO, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "customer/create";
        }
        customerService.save(customerDTO, user);
        return "redirect:/orders/checkout";
    }
}
