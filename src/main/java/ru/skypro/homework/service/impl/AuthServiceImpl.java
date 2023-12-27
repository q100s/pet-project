package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.UserDetailsManagerImpl;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.service.AuthService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManagerImpl manager;
    private final PasswordEncoder encoder;

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDto registerDto) {
        if (manager.userExists(registerDto.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(registerDto.getPassword())
                        .username(registerDto.getUsername())
                        .roles(registerDto.getRole().name())
                        .build());
        return true;
    }
}