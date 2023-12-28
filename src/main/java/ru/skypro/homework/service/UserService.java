package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.repository.UserRepository;

public interface UserService {
    void setPassword(NewPasswordDto newPasswordDto);
    UserDto getUserInfo();
    UpdateUserDto updateUser(UpdateUserDto updateUserDto);
    void updateUserImage(byte[] imageBytes);

    UserDto findByEmail(String email);
}