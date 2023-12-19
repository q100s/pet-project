package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;

@Service
@AllArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;

    @Override
    public AdsDto getAllAds() {
        return null;
    }

    @Override
    public AdDto addAd(CreateOrUpdateAdDto ad, byte[] imageBytes) {
        return null;
    }

    @Override
    public CommentsDto getComments(Integer id) {
        return null;
    }

    @Override
    public CreateOrUpdateCommentDto addComment(Integer id, CreateOrUpdateCommentDto newComment) {
        return null;
    }

    @Override
    public ExtendedAdDto getAds(Integer id) {
        return null;
    }

    @Override
    public void removeAd(Integer id) {

    }

    @Override
    public AdDto updateAds(Integer id, CreateOrUpdateAdDto ad) {
        return null;
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {

    }

    @Override
    public CreateOrUpdateCommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto comment) {
        return null;
    }

    @Override
    public AdsDto getMyAds() {
        return null;
    }

    @Override
    public void updateImage(Integer id, MultipartFile image) {

    }
    public Comment getByAdId(Integer id) {
        return commentRepository.findByAdId(id);
    }
}