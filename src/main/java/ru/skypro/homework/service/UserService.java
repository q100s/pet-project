package ru.skypro.homework.service;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.UserRepository;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    public User createUser(User user) {
        logger.info("user was created");
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        logger.info("start method deleteUserById");
        User userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userCheck);
    }

    public List<User> getAllUsers() {
        logger.info("start method getAllUserFromUserRepository");
        return userRepository.findAll();
    }

    public User editUser(Long id, User user) {
        logger.info("start method editUser");
        User editedUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(user.getUserName()).ifPresent(editedUser::setUserName);
        Optional.ofNullable(user.getPassword()).ifPresent(editedUser::setPassword);
        Optional.ofNullable(user.getFirstName()).ifPresent(editedUser::setFirstName);
        Optional.ofNullable(user.getLastName()).ifPresent(editedUser::setLastName);
        Optional.ofNullable(user.getPhone()).ifPresent(editedUser::setPhone);
        return userRepository.save(user);

    }

    public User findUserById(Long id) {
        logger.info("start method findUserById");
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}