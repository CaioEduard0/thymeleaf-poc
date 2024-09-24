package com.example.thymeleafpoc.mapper;

import com.example.thymeleafpoc.dto.UserUpdateDTO;
import com.example.thymeleafpoc.model.Customer;
import com.example.thymeleafpoc.model.User;

public class UserMapper {

    public static UserUpdateDTO toUserUpdateDTO(User user) {
        Customer customer = user.getCustomer();
        return new UserUpdateDTO(user.getEmail(), customer.getName(), customer.getBirthDate(), customer.getCpf(), customer.getPhone(), customer.getAddress());
    }
}
