package com.example.thymeleafpoc.controller;

import com.example.thymeleafpoc.dto.CustomerDTO;
import com.example.thymeleafpoc.model.Customer;
import com.example.thymeleafpoc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String getCustomers(@RequestParam int page, @RequestParam("") String search) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Customer> customers = customerService.findAll(pageable, search);
        return "";
    }

    @GetMapping("/{id}")
    public String getCustomer(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        return "";
    }

    @PostMapping
    public String saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.save(customerDTO);
        return "";
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.update(id, customerDTO);
        return "";
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return "";
    }
}
