package com.jpsouza.webcrawler.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterUserFormDTO {
    @Min(value = 3, message = "Username inválido")
    private String username;
    @Email(message = "E-mail inválido")
    private String email;
    @Min(value = 8, message = "A senha deve conter pelo menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "A senha deve conter letras minusculas, maisculas e números")
    private String password;
    @Min(value = 3, message = "Nome deve conter ao menos 3 caracteres")
    private String name;
    @Pattern(regexp = "^$|\\s*(\\d{2}|\\d{0})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$")
    private String phone;
}
