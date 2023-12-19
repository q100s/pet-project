package ru.skypro.homework.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.UserServiceImpl;

@Service
@AllArgsConstructor
public class AdMapper {

    private final UserServiceImpl userService;
    public static AdDto mapFromAdEntityIntoAdDto(Ad entity) {
        AdDto dto = new AdDto();
        dto.setPk(entity.getId());
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setImageUrl(entity.getImageUrl());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        return dto;
    }

    public Ad mapFromAdDtoIntoAdEntity(AdDto dto) {
        Ad entity = new Ad();
        entity.setId(dto.getPk());
        entity.setAuthor(userService.findById(entity.getAuthor().getId()));
        entity.setImageUrl(dto.getImageUrl());
        entity.setPrice(dto.getPrice());
        entity.setTitle(dto.getTitle());
        return entity;
    }

    public static CreateOrUpdateAdDto mapFromAdEntityIntoCreateOrUpdateAdDto(Ad entity) {
        CreateOrUpdateAdDto dto = new CreateOrUpdateAdDto();
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setProperties(entity.getTitle());
        return dto;
    }

    public static Ad mapFromCreateOrUpdateAdDtoIntoAdEntity(CreateOrUpdateAdDto dto) {
        Ad entity = new Ad();
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getProperties());
        return entity;
    }

    public static ExtendedAdDto mapFromAdEntityIntoExtendedAdDto(Ad entity) {
        ExtendedAdDto dto = new ExtendedAdDto();
        dto.setPk(entity.getId());
        dto.setAuthorFirstName(entity.getAuthor().getFirstName());
        dto.setAuthorLastName(entity.getAuthor().getLastName());
        dto.setDescription(entity.getDescription());
        dto.setAuthorEmail(entity.getAuthor().getEmail());
        dto.setAdImageUrl(entity.getImageUrl());
        dto.setAuthorPhoneNumber(entity.getAuthor().getPhoneNumber());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        return dto;
    }

    public Ad mapFromExtendedAdDtoIntoAdEntity(ExtendedAdDto dto) {
        Ad entity = new Ad();
        entity.setId(dto.getPk());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setTitle(dto.getTitle());
        entity.setAuthor(userService.findById(dto.getPk()));
        return entity;
    }
}
