package ru.skypro.homework.dto.Mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserMapper {
    public UserDto fromUserEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setRole(user.getRole());
        return userDto;
    }
    public User fromUserDto(UserDto userDto) {
        User user = new User();
        user.setUserName(user.getUserName());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setPhone(user.getPhone());
        user.setRole(user.getRole());
        return user;
    }



}
