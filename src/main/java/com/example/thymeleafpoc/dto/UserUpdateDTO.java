package com.example.thymeleafpoc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    private String email;
    @NotBlank(message = "Preencha o nome.")
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    private String cpf;
    @NotBlank(message = "Preencha o número de telefone.")
    private String phone;
    @NotBlank(message = "Preencha o endereço.")
    private String address;
}
