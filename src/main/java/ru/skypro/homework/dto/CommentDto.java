package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer authorId;
    private String authorImageUrl;
    private String authorName;
    private Integer creationTime;
    private Integer pk;
    private String text;
}