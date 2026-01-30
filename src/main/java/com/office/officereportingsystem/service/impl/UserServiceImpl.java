package com.office.officereportingsystem.service.impl;

import com.office.officereportingsystem.dto.request.UserCreateRequestDtos;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.enums.Role;
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
    public User createUser(UserCreateRequestDtos dto) {
        String normalizedEmail = dto.getEmail().trim().toLowerCase();

//        if (userRepo.findByEmail(normalizedEmail).isPresent()) {
//            throw new UserAlreadyExistsException("USER_EMAIL_ALREADY_EXISTS", dto);
//        }

        User user = User.builder()
                .firstName(dto.getFirstName().trim())
                .middleName(dto.getMiddleName().trim())
                .lastName(dto.getLastName().trim())
                .email(normalizedEmail)
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole() != null ? dto.getRole() : Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        return userRepo.save(user);
    }
}
