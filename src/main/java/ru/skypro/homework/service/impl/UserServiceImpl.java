package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.dto.mapper.UserMapper;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void setPassword(NewPasswordDto newPasswordDto) {

    }

    @Override
    public UserDto getUserInfo() {
        return null;
    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto) {
        return null;
    }

    @Override
    public void updateUserImage(byte[] imageBytes) {

    }

    @Override
    public UserDto findByEmail(String email) {
        User findedUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return UserMapper.mapFromUserEntityIntoUserDto(findedUser);
    }
}