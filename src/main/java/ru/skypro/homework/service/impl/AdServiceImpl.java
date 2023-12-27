package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.AccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.dto.mapper.AdMapper;
import ru.skypro.homework.exception.AccessDeniedException;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.exception.UserUnauthorizedException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.security.AccessControlException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {
    private final ImageService imageService;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    /**
     * Метод возвращает коллекцию всех объявлений. <br>
     * #{@link AdRepository#findAll()} <br>
     *
     * @return коллекция всех объявлений, хранящихся в базе данных в формате {@link AdDto}
     */
    @Override
    public AdsDto getAllAds() {
        List<AdDto> allAds = adRepository.findAll().stream()
                .map(AdMapper::mapFromAdEntityIntoAdDto)
                .collect(Collectors.toList());
        return new AdsDto(allAds.size(), allAds);
    }

    /**
     * Метод возвращает информацию об объявлении по переданному идентификатору. <br>
     * #{@link UserRepository#findByEmail(String)}. <br>
     * #{@link ImageService#saveToDataBase(MultipartFile)}
     *
     * @param adDto          объявление в формате {@link CreateOrUpdateAdDto}
     * @param image          картинка в формате {@link MultipartFile}
     * @param authentication
     * @return возвращает модель объявления в формате {@link AdDto}
     */
    @Override
    public AdDto addAd(CreateOrUpdateAdDto adDto, MultipartFile image, Authentication authentication) throws IOException {
        if (authentication.isAuthenticated()) {
            Image newImage = imageService.saveToDataBase(image);
            Ad ad = AdMapper.mapFromCreateOrUpdateAdDtoIntoAdEntity(adDto);
            ad.setAuthor(userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new));
            ad.setImage(newImage);
            ad.setImageUrl("/images/" + newImage.getId());
            return AdMapper.mapFromAdEntityIntoAdDto(ad);
        } else {
            throw new UserUnauthorizedException();
        }
    }

    /**
     * Метод возвращает информацию об объявлении по переданному идентификатору
     * #{@link AdRepository#findById(Object)} <br>
     *
     * @param id идентификатор объявления
     * @return возвращает модель объявления в формате {@link ExtendedAdDto}
     */
    @Override
    public ExtendedAdDto getAds(Integer id, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
            return AdMapper.mapFromAdEntityIntoExtendedAdDto(ad);
        } else {
            throw new UserUnauthorizedException();
        }
    }

    @Override
    public void removeAd(Integer id, Authentication authentication) {
        Ad deletedAd = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        if (isAdmin(authentication) || isAdsOwner(authentication, authentication.getName())) {
            adRepository.delete(deletedAd);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public AdDto updateAds(Integer id, CreateOrUpdateAdDto ad, Authentication authentication) {
        Ad newProperties = AdMapper.mapFromCreateOrUpdateAdDtoIntoAdEntity(ad);
        Ad updatedAd = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        if (isAdmin(authentication) || isAdsOwner(authentication, authentication.getName())) {
            Optional.ofNullable(newProperties.getPrice()).ifPresent(updatedAd::setPrice);
            Optional.ofNullable(newProperties.getTitle()).ifPresent(updatedAd::setTitle);
            Optional.ofNullable(newProperties.getDescription()).ifPresent(updatedAd::setDescription);
            adRepository.save(updatedAd);
            return AdMapper.mapFromAdEntityIntoAdDto(updatedAd);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public AdsDto getMyAds(Authentication authentication) {
        Integer myId = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new).getId();
        List<AdDto> allMyAds = adRepository.findAll().stream()
                .map(AdMapper::mapFromAdEntityIntoAdDto)
                .filter(ad -> ad.getPk().equals(myId))
                .collect(Collectors.toList());
        return new AdsDto(allMyAds.size(), allMyAds);
    }

    @Override
    public void updateImage(Integer id, MultipartFile image, Authentication authentication) {

    }

    private boolean isAdsOwner(Authentication authentication, String userName) {
        return authentication.getName().equals(userName);
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
                .contains("ROLE_ADMIN");
    }
}