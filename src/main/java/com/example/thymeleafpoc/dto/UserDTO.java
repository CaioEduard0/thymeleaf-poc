package com.example.thymeleafpoc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Preencha o email.")
    @Email(message = "O e-mail está incorreto.")
    private String email;
    @NotBlank(message = "Preencha a senha.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String password;
    @NotBlank(message = "Preencha a confirmação de senha.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String confirmPassword;
}
