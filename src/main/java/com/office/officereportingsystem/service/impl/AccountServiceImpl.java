package com.office.officereportingsystem.service.impl;

import com.office.officereportingsystem.converter.AccountConverter;
import com.office.officereportingsystem.dto.request.AccountCreateRequestDto;
import com.office.officereportingsystem.dto.request.AccountUpdateRequestDto;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.enums.Role;
import com.office.officereportingsystem.exception.AccountNotFoundException;
import com.office.officereportingsystem.exception.UserAlreadyExistsException;
import com.office.officereportingsystem.repository.UserRepo;
import com.office.officereportingsystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AccountConverter accountConverter;

    @Override
    public void createAdmin(AccountCreateRequestDto dto) {
        createAccount(dto, Role.ADMIN);
    }

    @Override
    public void createUser(AccountCreateRequestDto dto) {
        createAccount(dto, Role.USER);
    }


    private void createAccount(AccountCreateRequestDto dto, Role role) {

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
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();

        userRepo.save(user);
    }

    @Override
    public void updateAccount(Integer accountId, AccountUpdateRequestDto dto) {

        User user = userRepo.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));

        if (!user.getEmail().equalsIgnoreCase(dto.getEmail())) {
            userRepo.findByEmail(dto.getEmail().trim().toLowerCase()).ifPresent(existingUser -> {
                throw new UserAlreadyExistsException("USER_EMAIL_ALREADY_EXISTS", dto);
            });
            user.setEmail(dto.getEmail().trim().toLowerCase());
        }

        user.setFirstName(dto.getFirstName().trim());
        user.setMiddleName(dto.getMiddleName());
        user.setLastName(dto.getLastName().trim());

        userRepo.save(user);
    }

    @Override
    public void deleteAccount(Integer targetUserId, String currentUserEmail) {
        // Fetch the user who is currently logged in
        User currentUser = userRepo.findByEmail(currentUserEmail)
                .orElseThrow(() -> new AccountNotFoundException("CURRENT_USER_NOT_FOUND"));

        // Fetch the user to be deleted
        User targetUser = userRepo.findById(targetUserId)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));

        // Prevent deleting superadmin
        if (targetUser.getRole() == Role.SUPER_ADMIN) {
            throw new IllegalArgumentException("CANNOT_DELETE_SUPERADMIN");
        }

        // Prevent admin from deleting themselves
        if (currentUser.getId().equals(targetUserId)) {
            throw new IllegalArgumentException("CANNOT_DELETE_SELF");
        }

        // If all checks pass, delete the target user
        userRepo.deleteById(targetUserId);
    }


}
