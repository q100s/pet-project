package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.UserDetailsManagerImpl;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.dto.mapper.UserMapper;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserDetailsManagerImpl manager;

    @Override
    public void setPassword(NewPasswordDto newPasswordDto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        String oldPassword = newPasswordDto.getCurrentPassword();
        String newPassword = newPasswordDto.getNewPassword();
        manager.changePassword(oldPassword, newPassword, user);
    }

    @Override
    public UserDto getUserInfo(Authentication authentication) {
        return UserMapper.mapFromUserEntityIntoUserDto(userRepository.findByEmail(authentication.getName()).
                orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserDto updateUser(UpdateUserDto newProperties, Authentication authentication) {
        User updatedUser = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(newProperties.getFirstName()).ifPresent(updatedUser::setFirstName);
        Optional.ofNullable(newProperties.getLastName()).ifPresent(updatedUser::setLastName);
        Optional.ofNullable(newProperties.getPhone()).ifPresent(updatedUser::setPhoneNumber);
        userRepository.save(updatedUser);
        return UserMapper.mapFromUserEntityIntoUserDto(updatedUser);
    }

    @Override
    public void updateUserImage(MultipartFile image, Authentication authentication) throws IOException {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Image usersImage = user.getImage();
        Image newImage = imageService.saveToDataBase(image);
        if (user.getImage() != null) {
            imageService.deleteImage(usersImage);
        }
        user.setImage(newImage);
        user.setImageUrl("/images/" + newImage.getId());
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}