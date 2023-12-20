package ru.skypro.homework.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;

@Service
@AllArgsConstructor
public class CommentMapper {
    private final UserRepository userRepository;
    public static CommentDto mapFromCommentEntityIntoCommentDto(Comment entity) {
        CommentDto dto = new CommentDto();
        dto.setPk(entity.getId());
        dto.setCreationTime(entity.getCreatedAt());
        dto.setText(entity.getText());
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setAuthorName(entity.getAuthor().getFirstName() + entity.getAuthor().getLastName());
        dto.setAuthorImageUrl(entity.getAuthor().getImageUrl());
        return dto;
    }

    public Comment mapFromCommentDtoIntoCommentEntity(CommentDto dto) {
        Comment entity = new Comment();
        entity.setId(dto.getPk());
        entity.setCreatedAt(dto.getCreationTime());
        entity.setText(dto.getText());
        entity.setAuthor(userRepository.getReferenceById(entity.getAuthor().getId()));
        return entity;
    }

    public static CreateOrUpdateCommentDto mapFromCommentEntityIntoCreateOrUpdateCommentDto(Comment entity) {
        CreateOrUpdateCommentDto dto = new CreateOrUpdateCommentDto();
        dto.setText(entity.getText());
        return dto;
    }

    public Comment mapCreateOrUpdateCommentDtoCommentEntity(CreateOrUpdateCommentDto dto) {
        Comment entity = new Comment();
        entity.setText(dto.getText());
        return entity;
    }
}