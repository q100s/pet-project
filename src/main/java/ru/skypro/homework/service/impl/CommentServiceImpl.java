package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.dto.mapper.CommentMapper;
import ru.skypro.homework.exception.AccessDeniedException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.UserUnauthorizedException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final AdService adService;

    @Override
    public CommentsDto getCommentsById(Integer id, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            List<CommentDto> allComments = commentRepository.findByAdId(id).stream()
                    .map(CommentMapper::mapFromCommentEntityIntoCommentDto)
                    .collect(Collectors.toList());
            return new CommentsDto(allComments.size(), allComments);
        } else {
            throw new UserUnauthorizedException();
        }
    }

    @Override
    public CommentDto addComment(Integer id, Authentication authentication, CreateOrUpdateCommentDto newComment) {
        if (authentication.isAuthenticated()) {
            Ad adForComment = adService.getById(id);
            User author = userService.findByEmail(authentication.getName());

            Comment commentToAdd = new Comment();
            commentToAdd.setText(newComment.getText());
            commentToAdd.setCreatedAt((int) System.currentTimeMillis());
            commentToAdd.setAuthor(author);
            commentToAdd.setAd(adForComment);
            commentRepository.save(commentToAdd);

            author.getComments().add(commentToAdd);
            userService.createUser(author);

            return CommentMapper.mapFromCommentEntityIntoCommentDto(commentToAdd);
        } else {
            throw new UserUnauthorizedException();
        }
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId, Authentication authentication) {
        Comment commentToDelete = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        String commentAuthorName = commentToDelete.getAuthor().getEmail();
        if (ValidationService.isAdmin(authentication) || ValidationService.isOwner(authentication, commentAuthorName)) {
            adService.getById(adId).getComments().remove(commentToDelete);
            commentRepository.delete(commentToDelete);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public CreateOrUpdateCommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto comment,
                                                  Authentication authentication) {
        Comment commentToUpdate = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        Ad adToUpdate = adService.getById(adId);
        String commentAuthorName = commentToUpdate.getAuthor().getEmail();
        if (ValidationService.isAdmin(authentication) || ValidationService.isOwner(authentication, commentAuthorName)) {
            adToUpdate.getComments().remove(commentToUpdate);
            commentToUpdate.setText(comment.getText());
            adToUpdate.getComments().add(commentToUpdate);
            commentRepository.save(commentToUpdate);
            adService.createAd(adToUpdate);
            return CommentMapper.mapFromCommentEntityIntoCreateOrUpdateCommentDto(commentToUpdate);
        } else {
            throw new AccessDeniedException();
        }
    }
}