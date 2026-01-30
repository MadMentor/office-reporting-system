package com.office.officereportingsystem.service.impl;

import com.office.officereportingsystem.converter.AdminConverter;
import com.office.officereportingsystem.dto.request.AdminCreateRequestDto;
import com.office.officereportingsystem.dto.request.AdminUpdateRequestDto;
import com.office.officereportingsystem.dto.response.AdminResponseDto;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.enums.Role;
import com.office.officereportingsystem.exception.AccountNotFoundException;
import com.office.officereportingsystem.exception.UserAlreadyExistsException;
import com.office.officereportingsystem.repository.UserRepo;
import com.office.officereportingsystem.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AdminConverter adminConverter;


    @Override
    public AdminUpdateRequestDto getAdminById(Integer id) throws IOException {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));

        return AdminUpdateRequestDto.builder()
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public void createAdmin(AdminCreateRequestDto dto) {

        String normalizedEmail = isEmailUsed(dto);

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

    @Override
    public List<AdminResponseDto> getAllAdmins() {
        return userRepo.findByRole(Role.ADMIN)
                .stream()
                .map(user -> {
                    try {
                        return adminConverter.toDto(user);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to convert User to AdminResponseDto");
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateAdmin(Integer id, AdminUpdateRequestDto dto) {
        User admin = userRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));

        userRepo.findByEmail(dto.getEmail())
                .filter(u -> !u.getId().equals(id)) // ignore current admin
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException("EMAIL_ALREADY_IN_USE", dto);
                });

        admin.setFirstName(dto.getFirstName());
        admin.setMiddleName(dto.getMiddleName());
        admin.setLastName(dto.getLastName());
        admin.setEmail(dto.getEmail());
        userRepo.save(admin);
    }

    @Override
    public void deleteAdmin(Integer adminId) {
        userRepo.deleteById(adminId);
    }

    private String isEmailUsed(AdminCreateRequestDto dto) {
        String normalizedEmail = dto.getEmail().trim().toLowerCase();

        if (userRepo.findByEmail(normalizedEmail).isPresent()) {
            throw new UserAlreadyExistsException("USER_EMAIL_ALREADY_EXISTS", dto);
        }
        return normalizedEmail;
    }


}
