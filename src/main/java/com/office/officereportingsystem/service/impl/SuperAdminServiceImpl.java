package com.office.officereportingsystem.service.impl;

import com.office.officereportingsystem.dto.request.AdminCreateRequestDto;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.enums.Role;
import com.office.officereportingsystem.exception.UserAlreadyExistsException;
import com.office.officereportingsystem.repository.UserRepo;
import com.office.officereportingsystem.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SuperAdminServiceImpl implements SuperAdminService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createAdmin(AdminCreateRequestDto dto) {
        String normalizedEmail = dto.getEmail().trim().toLowerCase();

        if (userRepo.findByEmail(normalizedEmail).isPresent()) {
            throw new UserAlreadyExistsException("USER_EMAIL_ALREADY_EXISTS", dto);
        }

        User user = User.builder()
                .firstName(dto.getFirstName().trim())
                .middleName(dto.getMiddleName() == null ? null : dto.getMiddleName().trim())
                .lastName(dto.getLastName().trim())
                .email(normalizedEmail)
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.ADMIN)
                .createdAt(LocalDateTime.now())
                .build();

        userRepo.save(user);
    }
}
