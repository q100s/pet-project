package ru.skypro.homework.dto.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

@Service
public class UserMapper {
    public static UserDto mapFromUserEntityIntoUserDto(User entity) {
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setRole(entity.getRole());
        dto.setImageUrl(entity.getImageUrl());
        return dto;
    }

    public static User mapFromUserDtoIntoUserEntity(UserDto dto) {
        User entity = new User();
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setRole(dto.getRole());
        if (dto.getImageUrl() != null) {
            entity.setImageUrl(dto.getImageUrl());
        }
        return entity;
    }

    public static RegisterDto mapFromUserEntityIntoRegisterDto(User entity) {
        RegisterDto dto = new RegisterDto();
        dto.setUsername(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhoneNumber());
        dto.setRole(entity.getRole());
        return dto;
    }

    public static User mapFromRegisterDtoIntoUserEntity(RegisterDto dto) {
        User entity = new User();
        entity.setEmail(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhone());
        entity.setRole(dto.getRole());
        return entity;
    }

    public static UpdateUserDto mapFromUserEntityIntoUpdateUserDto(User entity) {
        UpdateUserDto dto = new UpdateUserDto();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        return dto;
    }

    public static User mapFromUpdateUserDtoIntoUserEntity(UpdateUserDto dto) {
        User entity = new User();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }
}