package com.example.thymeleafpoc.service;

import com.example.thymeleafpoc.dto.PasswordDTO;
import com.example.thymeleafpoc.dto.UserDTO;
import com.example.thymeleafpoc.dto.UserUpdateDTO;
import com.example.thymeleafpoc.model.User;
import com.example.thymeleafpoc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(int page, String search) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> users;
        if (search == null || search.isEmpty()) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.findAll(search, pageable);
        }
        users.getContent().forEach(user -> user.setPassword(null));
        return users;
    }

    @Transactional
    public void save(UserDTO userDTO) {
        User user = User.builder()
                        .email(userDTO.getEmail())
                        .password(passwordEncoder.encode(userDTO.getPassword()))
                        .authorities("USER")
                        .active(true)
                        .build();
        userRepository.save(user);
    }

    @Transactional
    public void update(UserUpdateDTO userUpdateDTO, User user) {
        user.getCustomer().setName(userUpdateDTO.getName());
        user.getCustomer().setPhone(userUpdateDTO.getPhone());
        user.getCustomer().setAddress(userUpdateDTO.getAddress());
        userRepository.save(user);
    }

    @Transactional
    public void makeAdmin(Long id) {
        userRepository.makeAdmin(id);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Transactional
    public void activate(Long id) {
        userRepository.activate(id);
    }

    @Transactional
    public void update(PasswordDTO passwordDTO, User user) {
        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        userRepository.save(user);
    }
}
