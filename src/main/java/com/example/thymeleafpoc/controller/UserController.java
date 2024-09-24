package com.example.thymeleafpoc.controller;

import com.example.thymeleafpoc.dto.PasswordDTO;
import com.example.thymeleafpoc.dto.UserDTO;
import com.example.thymeleafpoc.dto.UserUpdateDTO;
import com.example.thymeleafpoc.mapper.UserMapper;
import com.example.thymeleafpoc.model.User;
import com.example.thymeleafpoc.service.UserService;
import com.example.thymeleafpoc.utils.PageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final String REDIRECT_USERS = "redirect:/users";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String findAll(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String search, @AuthenticationPrincipal User user) {
        Page<User> users = userService.findAll(page, search);
        model.addAttribute("users", users);
        PageUtils.format(users, model);
        return "user/list";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "user/create";
    }

    @PostMapping
    public String save(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userDTO", "password", "As senhas não são iguais."));
        }
        if (bindingResult.hasErrors()) {
            return "user/create";
        }
        userService.save(userDTO);
        return "redirect:/login?success";
    }

    @GetMapping("/update")
    public String updatePage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("userUpdateDTO", UserMapper.toUserUpdateDTO(user));
        return "user/update";
    }

    @PutMapping
    public String update(@ModelAttribute @Valid UserUpdateDTO userUpdateDTO, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "user/update";
        }
        userService.update(userUpdateDTO, user);
        return "redirect:/?success";
    }

    @GetMapping("/{id}/admin")
    public String makeAdmin(@PathVariable Long id) {
        userService.makeAdmin(id);
        return REDIRECT_USERS;
    }

    @GetMapping("/{id}/disable")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return REDIRECT_USERS;
    }

    @GetMapping("/{id}/activate")
    public String activate(@PathVariable Long id) {
        userService.activate(id);
        return REDIRECT_USERS;
    }

    @GetMapping("/password")
    public String changePassword(Model model) {
        model.addAttribute("passwordDTO", new PasswordDTO());
        return "user/password";
    }

    @PutMapping("/password")
    public String update(@ModelAttribute @Valid PasswordDTO passwordDTO, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (!passwordEncoder.matches(passwordDTO.getPassword(), user.getPassword())) {
            bindingResult.addError(new FieldError("passwordDTO", "password", "A senha atual está incorreta."));
        }
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            bindingResult.addError(new FieldError("passwordDTO", "newPassword", "As senhas não são iguais."));
        }
        if (bindingResult.hasErrors()) {
            return "user/password";
        }
        userService.update(passwordDTO, user);
        return "redirect:/?passwordChanged";
    }
}
