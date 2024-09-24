package com.example.thymeleafpoc.dto;

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
public class PasswordDTO {

    @NotBlank(message = "Preencha a senha atual.")
    private String password;
    @NotBlank(message = "Preencha a nova senha.")
    @Size(min = 6, message = "A nova senha deve ter pelo menos 6 caracteres.")
    private String newPassword;
    @NotBlank(message = "Preencha a confirmação da nova senha.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String confirmPassword;
}
