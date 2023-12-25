package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

public interface AdService {
    AdsDto getAllAds();
    AdDto addAd(CreateOrUpdateAdDto ad, byte[] imageBytes);
    CommentsDto getComments(Integer id);
    CreateOrUpdateCommentDto addComment(Integer id, CreateOrUpdateCommentDto newComment);
    ExtendedAdDto getAds(Integer id);
    void removeAd(Integer id);
    AdDto updateAds(Integer id, CreateOrUpdateAdDto ad);
    void deleteComment(Integer adId, Integer commentId);
    CreateOrUpdateCommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto comment);
    AdsDto getMyAds();
    void updateImage(Integer id, MultipartFile image);
}