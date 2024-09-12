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
    @NotBlank(message = "Preencha o email.")
    private String email;
    @NotNull(message = "Preencha a data de nascimento")
    @Past(message = "Data de nascimento inválida")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    private boolean active;
}
