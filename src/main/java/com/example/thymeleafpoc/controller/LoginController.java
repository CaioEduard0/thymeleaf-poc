package com.example.thymeleafpoc.controller;

import com.example.thymeleafpoc.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal User user) {
        user.getAuthorities().stream().findFirst().ifPresent(authority -> model.addAttribute("authority", authority));
        model.addAttribute("customer", user.getCustomer());
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
