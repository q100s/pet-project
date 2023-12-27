package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

import java.io.IOException;

public interface AdService {
    AdsDto getAllAds();
    AdDto addAd(CreateOrUpdateAdDto ad, MultipartFile imageBytes, Authentication authentication) throws IOException;
    ExtendedAdDto getAds(Integer id, Authentication authentication);
    void removeAd(Integer id, Authentication authentication);
    AdDto updateAds(Integer id, CreateOrUpdateAdDto ad, Authentication authentication);
    AdsDto getMyAds(Authentication authentication);
    void updateImage(Integer id, MultipartFile image, Authentication authentication);
}