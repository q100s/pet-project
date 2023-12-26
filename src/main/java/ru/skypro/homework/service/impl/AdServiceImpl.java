package ru.skypro.homework.service.impl;

import liquibase.pro.packaged.I;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.dto.mapper.AdMapper;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;

    /**
     * Метод возвращает коллекцию всех объявлений в формате {@link AdDto}. <br>
     * #{@link AdRepository#findAll()} <br>
     * @return коллекция всех объявлений, хранящихся в базе данных
     */
    @Override
    public AdsDto getAllAds() {
        List<AdDto> allAds = adRepository.findAll().stream()
                .map(AdMapper::mapFromAdEntityIntoAdDto)
                .collect(Collectors.toList());
        return new AdsDto(allAds.size(), allAds);
    }

    @Override
    public AdDto addAd(CreateOrUpdateAdDto ad, byte[] imageBytes) {
        return null;
    }

    /**
     * Метод получает информацию об объявлении по переданному идентификатору
     *#{@link AdRepository#findById(Object)} <br>
     * @param id идентификатор объявления
     * @return возвращает модель объявления в формате {@link ExtendedAdDto}
     */
    @Override
    public ExtendedAdDto getAds(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        return AdMapper.mapFromAdEntityIntoExtendedAdDto(ad);
    }

    @Override
    public void removeAd(Integer id) {

    }

    @Override
    public AdDto updateAds(Integer id, CreateOrUpdateAdDto ad) {
        return null;
    }

    @Override
    public AdsDto getMyAds() {
        return null;
    }

    @Override
    public void updateImage(Integer id, MultipartFile image) {

    }
}