package com.example.thymeleafpoc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
public class CustomerDTO {

    @NotBlank(message = "Preencha o nome.")
    private String name;
    @NotNull(message = "Preencha a data de nascimento.")
    @Past(message = "Data de nascimento inválida.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    @NotBlank(message = "Preencha o cpf.")
    private String cpf;
    @NotBlank(message = "Preencha o número de telefone.")
    private String phone;
    @NotBlank(message = "Preencha o endereço.")
    private String address;
}
