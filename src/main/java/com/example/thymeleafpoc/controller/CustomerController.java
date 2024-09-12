package com.example.thymeleafpoc.controller;

import com.example.thymeleafpoc.dto.CustomerDTO;
import com.example.thymeleafpoc.mapper.CustomerMapper;
import com.example.thymeleafpoc.model.Customer;
import com.example.thymeleafpoc.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private static final String REDIRECT_CUSTOMER = "redirect:/customer";
    private final CustomerService customerService;

    @GetMapping
    public String getCustomers(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Customer> customers = customerService.findAll(pageable, search);
        model.addAttribute("customers", customers);
        model.addAttribute("customerDTO", new CustomerDTO());
        return "customer/list";
    }

    @GetMapping("/create")
    public String createCustomer(Model model) {
        model.addAttribute("customerDTO", new CustomerDTO());
        return "customer/create";
    }
    @PostMapping
    public String saveCustomer(@ModelAttribute @Valid CustomerDTO customerDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "customer/create";

        Customer customer = customerService.save(customerDTO);
        return REDIRECT_CUSTOMER;
    }

    @GetMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, Model model) {
        CustomerDTO customerDTO = CustomerMapper.toCustomerDTO(customerService.findById(id));
        model.addAttribute("customerDTO", customerDTO);
        model.addAttribute("id", id);
        return "customer/update";
    }
    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute @Valid CustomerDTO customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "customer/update";

        customerService.update(id, customerDTO);
        return REDIRECT_CUSTOMER;
    }

    @GetMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return REDIRECT_CUSTOMER;
    }
}
