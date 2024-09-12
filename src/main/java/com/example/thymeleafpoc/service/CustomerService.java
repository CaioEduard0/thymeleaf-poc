package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.dto.CustomerDTO;
import com.example.thymeleafpoc.mapper.CustomerMapper;
import com.example.thymeleafpoc.model.Customer;
import com.example.thymeleafpoc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Page<Customer> findAll(Pageable pageable, String search) {

        if (search == null || search.isEmpty())
            return customerRepository.findAll(pageable);

        return customerRepository.findAllBySearch(search, pageable);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer save(CustomerDTO customerDTO) {
        return customerRepository.save(CustomerMapper.toCustomer(customerDTO));
    }

    public Customer update(Long id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null)
            return null;

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setBirthDate(customerDTO.getBirthDate());

        return customerRepository.save(customer);

    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

}
