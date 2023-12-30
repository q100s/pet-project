package ru.skypro.homework.dto.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.model.Comment;

@Component
public class CommentMapper {
    public static CommentDto mapFromCommentEntityIntoCommentDto(Comment entity) {
        CommentDto dto = new CommentDto();
        dto.setPk(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setText(entity.getText());
        dto.setAuthor(entity.getAuthor().getId());
        dto.setAuthorFirstName(entity.getAuthor().getFirstName());
        dto.setAuthorImage(entity.getAuthor().getImageUrl());
        return dto;
    }

    public static CreateOrUpdateCommentDto mapFromCommentEntityIntoCreateOrUpdateCommentDto(Comment entity) {
        CreateOrUpdateCommentDto dto = new CreateOrUpdateCommentDto();
        dto.setText(entity.getText());
        return dto;
    }
}