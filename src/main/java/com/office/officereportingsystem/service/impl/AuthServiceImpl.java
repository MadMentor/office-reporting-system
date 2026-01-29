package com.office.officereportingsystem.service.impl;

import com.office.officereportingsystem.dto.request.LoginRequestDto;
import com.office.officereportingsystem.dto.response.LoginResponseDto;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.exception.AuthenticationFailedException;
import com.office.officereportingsystem.repository.UserRepo;
import com.office.officereportingsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {

        User user = userRepo.findByEmail(dto.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new AuthenticationFailedException("INVALID_CREDENTIALS"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException("INVALID_CREDENTIALS");
        }

        return LoginResponseDto.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .messageCode("LOGIN_SUCCESS")
                .build();
    }
}
