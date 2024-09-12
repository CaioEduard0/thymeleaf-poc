package com.example.thymeleafpoc.mapper;

import com.example.thymeleafpoc.dto.CustomerDTO;
import com.example.thymeleafpoc.model.Customer;

public class CustomerMapper {
    public static CustomerDTO toCustomerDTO(Customer customer) {
        return new CustomerDTO(customer.getName(), customer.getEmail(), customer.getBirthDate(), customer.isActive());
    }

    public static Customer toCustomer(CustomerDTO customerDTO) {
        return new Customer(customerDTO.getName(), customerDTO.getEmail(), customerDTO.getBirthDate(), customerDTO.isActive());
    }
}
