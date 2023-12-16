package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserDto {
    @Size(min = 3, max = 10)
    private String firstName;
    @Size(min = 3, max = 10)
    private String lastName;
    private String phoneNumber;
}