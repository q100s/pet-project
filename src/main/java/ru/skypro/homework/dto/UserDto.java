package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UserDto {
    private String userName;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
