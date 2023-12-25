package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.CommentService;

public class CommentServiceImpl implements CommentService {
    @Override
    public CommentsDto getComments(Integer id) {
        return null;
    }

    @Override
    public CreateOrUpdateCommentDto addComment(Integer id, CreateOrUpdateCommentDto newComment) {
        return null;
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {

    }

    @Override
    public CreateOrUpdateCommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto comment) {
        return null;
    }
}