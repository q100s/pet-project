package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Role;

import javax.validation.constraints.Size;

@Data
public class RegisterDto {
    @Size(min = 4, max = 32)
    private String username;
    @Size(min = 8, max = 16)
    private String password;
    @Size(min = 2, max = 16)
    private String firstName;
    @Size(min = 2, max = 16)
    private String lastName;
    private String phone;
    private Role role;
}