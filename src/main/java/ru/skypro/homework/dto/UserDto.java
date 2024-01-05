package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.constant.Role;

@Data
public class UserDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;
}