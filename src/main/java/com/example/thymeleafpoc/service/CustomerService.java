package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.dto.CustomerDTO;
import com.example.thymeleafpoc.model.Customer;
import com.example.thymeleafpoc.model.User;
import com.example.thymeleafpoc.repository.CustomerRepository;
import com.example.thymeleafpoc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public Customer find(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void save(CustomerDTO customerDTO, User user) {
        Customer customer = Customer.builder()
                                    .name(customerDTO.getName())
                                    .birthDate(customerDTO.getBirthDate())
                                    .cpf(customerDTO.getCpf())
                                    .phone(customerDTO.getPhone())
                                    .address(customerDTO.getAddress())
                                    .user(user)
                                    .build();
        user.setCustomer(customer);
        userRepository.save(user);
    }
}
