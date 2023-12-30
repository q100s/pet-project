package ru.skypro.homework.dto.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

@Service
public class UserMapper {
    public static UserDto mapFromUserEntityIntoUserDto(User entity) {
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhoneNumber());
        dto.setRole(entity.getRole());
        dto.setImage(entity.getImageUrl());
        return dto;
    }
}