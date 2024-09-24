package com.example.thymeleafpoc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                   .requestMatchers(HttpMethod.GET, "/login", "/users/create").permitAll()
                   .requestMatchers(HttpMethod.POST, "/login", "/users").permitAll()
                   .anyRequest().authenticated())

                   .formLogin(formLogin -> formLogin
                   .usernameParameter("email")
                   .defaultSuccessUrl("/", true)
                   .loginPage("/login"))

                   .logout(logout -> logout
                   .logoutSuccessUrl("/logout")
                   .deleteCookies("JSESSIONID")
                   .clearAuthentication(true)
                   .invalidateHttpSession(true))
                   .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
