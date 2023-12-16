package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class ExtendedAdDto {
    private Integer pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String authorEmail;
    private String adImageUrl;
    private String authorPhoneNumber;
    private Integer price;
    private String title;
}