package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

public interface AdService {
    AdsDto getAllAds();
    AdDto addAd(CreateOrUpdateAdDto ad, byte[] imageBytes);
    ExtendedAdDto getAds(Integer id);
    void removeAd(Integer id);
    AdDto updateAds(Integer id, CreateOrUpdateAdDto ad);
    AdsDto getMyAds();
    void updateImage(Integer id, MultipartFile image);
}