package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.dto.mapper.AdMapper;
import ru.skypro.homework.exception.AccessDeniedException;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.InvalidMediaTypeException;
import ru.skypro.homework.exception.UserUnauthorizedException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final ImageService imageService;
    private final UserService userService;

    /**
     * Метод возвращает коллекцию всех объявлений. <br>
     * {@link AdRepository#findAll()} <br>
     * @return коллекция всех объявлений, хранящихся в базе данных в формате {@link AdDto}
     */
    @Override
    public AdsDto getAllAds() {
        List<AdDto> allAds = adRepository.findAll().stream()
                .map(AdMapper::mapIntoAdDto)
                .collect(Collectors.toList());
        return new AdsDto(allAds.size(), allAds);
    }

    /**
     * Метод сохраняет новое объявление в базе данных. <br>
     * {@link UserRepository#findByEmail(String)}. <br>
     * {@link ImageService#saveToDataBase(MultipartFile)}
     * @param properties     объявление в формате {@link CreateOrUpdateAdDto}
     * @param image          картинка в формате {@link MultipartFile}
     * @param authentication
     * @return объявление в формате {@link AdDto}
     */
    @Override
    public AdDto addAd(CreateOrUpdateAdDto properties, MultipartFile image,
                       Authentication authentication) throws IOException {
        if (!ValidationService.isImage(image)) {
            throw new InvalidMediaTypeException();
        }
        if (authentication.isAuthenticated()) {
            Image newImage = imageService.saveToDataBase(image);
            Ad adEntity = AdMapper.mapIntoAdEntity(properties);
            adEntity.setAuthor(userService.findByEmail(authentication.getName()));
            adEntity.setImage(newImage);
            adEntity.setImageUrl("/images/" + newImage.getId());
            adRepository.save(adEntity);
            return AdMapper.mapIntoAdDto(adEntity);
        } else {
            throw new UserUnauthorizedException();
        }
    }

    /**
     * Метод возвращает информацию об объявлении, найденному по переданному идентификатору. <br>
     * {@link AdRepository#findById(Object)} <br>
     * @param id идентификатор объявления
     * @param authentication
     * @return объявление в формате {@link ExtendedAdDto}
     */
    @Override
    public ExtendedAdDto getAds(Integer id, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
            return AdMapper.mapIntoExtendedAdDto(ad);
        } else {
            throw new UserUnauthorizedException();
        }
    }

    /**
     * Метод удаляет объявление, найденное по переданному идентификатору. <br>
     * {@link AdRepository#findById(Object)} <br>
     * {@link AdRepository#delete(Object)} (Object)} <br>
     * {@link ImageService#getById(Integer)} <br>
     * {@link ImageService#deleteImage(Image)} <br>
     * {@link ValidationService#isAdmin(Authentication)} <br>
     * {@link ValidationService#isOwner(Authentication, String)} <br>
     *
     * @param id             идентификатор объявления
     * @param authentication
     */
    @Override
    public void removeAd(Integer id, Authentication authentication) {
        Ad deletedAd = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        Image deletedImage = imageService.getById(deletedAd.getImage().getId());
        String deletedAdAuthorName = deletedAd.getAuthor().getEmail();
        if (ValidationService.isAdmin(authentication) || ValidationService.isOwner(authentication, deletedAdAuthorName)) {
            adRepository.delete(deletedAd);
            imageService.deleteImage(deletedImage);
        } else {
            throw new AccessDeniedException();
        }
    }

    /**
     * Метод обновляет у найденноого по переданному идентификатору объявления поля, согласно новым полученным данным от
     * {@link CreateOrUpdateAdDto}. <br>
     * {@link AdRepository#findById(Object)} <br>
     * {@link AdRepository#save(Object)} <br>
     * {@link ValidationService#isAdmin(Authentication)} <br>
     * {@link ValidationService#isOwner(Authentication, String)} <br>
     * @param id идентификатор объявления
     * @param newProperties новые данные для объявления
     * @param authentication
     * @return обновленное объявление в формате {@link AdDto}
     */
    @Override
    public AdDto updateAds(Integer id, CreateOrUpdateAdDto newProperties, Authentication authentication) {
        Ad updatedAd = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        String updatedAdAuthorName = updatedAd.getAuthor().getEmail();
        if (ValidationService.isAdmin(authentication) || ValidationService.isOwner(authentication, updatedAdAuthorName)) {
            Optional.ofNullable(newProperties.getPrice()).ifPresent(updatedAd::setPrice);
            Optional.ofNullable(newProperties.getTitle()).ifPresent(updatedAd::setTitle);
            Optional.ofNullable(newProperties.getDescription()).ifPresent(updatedAd::setDescription);
            adRepository.save(updatedAd);
            return AdMapper.mapIntoAdDto(updatedAd);
        } else {
            throw new AccessDeniedException();
        }
    }

    /**
     * Метод возвращает коллекцию объявлений авторизированного пользователя. <br>
     * {@link UserService#findByEmail(String)}
     * {@link AdRepository#findAll()}
     * @param authentication
     * @return коллекцию объявлений в формате {@link AdsDto}
     */
    @Override
    public AdsDto getMyAds(Authentication authentication) {
        Integer myId = userService.findByEmail(authentication.getName()).getId();
        List<AdDto> allMyAds = adRepository.findAll().stream()
                .filter(ad -> ad.getAuthor().getId().equals(myId))
                .map(AdMapper::mapIntoAdDto)
                .collect(Collectors.toList());
        return new AdsDto(allMyAds.size(), allMyAds);
    }

    /**
     * Метод обновляет картинку объявления полученную в формте {@link MultipartFile}. <br>
     * {@link AdRepository#findById(Object)}
     * {@link AdRepository#save(Object)}
     * {@link ImageService#deleteImage(Image)}
     * {@link ImageService#saveToDataBase(MultipartFile)}
     * @param id идентификатор объявления
     * @param image новая картинка
     * @param authentication
     */
    @Override
    public void updateImage(Integer id, MultipartFile image, Authentication authentication) {
        if (!ValidationService.isImage(image)) {
            throw new InvalidMediaTypeException();
        }
        String adAuthorName = adRepository.findById(id).orElseThrow(AdNotFoundException::new).getAuthor().getEmail();
        if (ValidationService.isAdmin(authentication) || ValidationService.isOwner(authentication, adAuthorName)) {
            Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
            imageService.deleteImage(ad.getImage());
            try {
                Image newImage = imageService.saveToDataBase(image);
                ad.setImage(newImage);
                ad.setImageUrl("/images/" + newImage.getId());
                adRepository.save(ad);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } else {
            throw new AccessDeniedException();
        }
    }

    /**
     * Метод возвращает объявление, найденное по переданному идентификатору. <br>
     * {@link AdRepository#findById(Object)}
     * @param id идентификатор объявления
     * @return объявление в формате {@link Ad}
     */
    @Override
    public Ad getById(Integer id) {
        return adRepository.findById(id).orElseThrow(AdNotFoundException::new);
    }

    /**
     * Метод сохраняет полученное объявление в базу данных. <br>
     * {@link AdRepository#save(Object)}
     * @param ad объявление для сохранения
     * @return объявление в формате {@link Ad}
     */
    @Override
    public Ad createAd(Ad ad) {
        return adRepository.save(ad);
    }
}