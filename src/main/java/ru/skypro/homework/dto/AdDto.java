package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdDto {
    private Integer authorId;
    private String imageUrl;
    private Integer pk;
    private Integer price;
    private String title;
}