package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

public interface CommentService {
    CommentsDto getComments(Integer id);
    CreateOrUpdateCommentDto addComment(Integer id, CreateOrUpdateCommentDto newComment);

    void deleteComment(Integer adId, Integer commentId);
    CreateOrUpdateCommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto comment);
}
