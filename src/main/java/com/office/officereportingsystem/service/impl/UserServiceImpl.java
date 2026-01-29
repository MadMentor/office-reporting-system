package com.office.officereportingsystem.service.impl;

import com.office.officereportingsystem.dto.request.UserCreateRequestDto;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.enums.Role;
import com.office.officereportingsystem.exception.UserAlreadyExistsException;
import com.office.officereportingsystem.repository.UserRepo;
import com.office.officereportingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User createUser(UserCreateRequestDto dto) {
        String normalizedEmail = dto.getEmail().trim().toLowerCase();

        if (userRepo.findByEmail(normalizedEmail).isPresent()) {
            throw new UserAlreadyExistsException("Email already in use: " + normalizedEmail);
        }

        User user = User.builder()
                .firstName(dto.getFirstName().trim())
                .middleName(dto.getMiddleName().trim())
                .lastName(dto.getLastName().trim())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole() != null ? dto.getRole() : Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        return userRepo.save(user);
    }
}
