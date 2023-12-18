package ru.skypro.homework.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Mapper.UserMapper;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.model.User;
import ru.skypro.homework.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private UserMapper userMapper;

    private UserRepository userRepository;

    public User createUser(User user) {
        log.info("user was created");
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        log.info("start method deleteUserById");
        User userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userCheck);
    }

    public List<UserDto> getAllUsers() {
        log.info("start method getAllUserFromUserRepository");
        return userRepository.findAll().stream()
                .map(user -> userMapper.fromUserEntity(user))
                .collect(Collectors.toList());
    }

    public User editUser(Long id, User user) {
        log.info("start method editUser");
        User editedUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(user.getUserName()).ifPresent(editedUser::setUserName);
        Optional.ofNullable(user.getPassword()).ifPresent(editedUser::setPassword);
        Optional.ofNullable(user.getFirstName()).ifPresent(editedUser::setFirstName);
        Optional.ofNullable(user.getLastName()).ifPresent(editedUser::setLastName);
        Optional.ofNullable(user.getPhone()).ifPresent(editedUser::setPhone);
        return userRepository.save(user);

    }

    public UserDto findUserById(Long id) {
        log.info("start method findUserById");
        User user= userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.fromUserEntity(user);

    }
}