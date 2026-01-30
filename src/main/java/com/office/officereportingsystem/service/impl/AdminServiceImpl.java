package com.office.officereportingsystem.service.impl;

import com.office.officereportingsystem.converter.AccountConverter;
import com.office.officereportingsystem.dto.request.AccountUpdateRequestDto;
import com.office.officereportingsystem.dto.response.AccountResponseDto;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.enums.Role;
import com.office.officereportingsystem.exception.AccountNotFoundException;
import com.office.officereportingsystem.repository.UserRepo;
import com.office.officereportingsystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AccountConverter accountConverter;

    @Override
    public AccountUpdateRequestDto getUserById(Integer id) throws IOException {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("USER_NOT_FOUND"));

        return AccountUpdateRequestDto.builder()
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }


    @Override
    public List<AccountResponseDto> getAllUsers() {
        return userRepo.findByRole(Role.USER)
                .stream()
                .map(user -> {
                    try {
                        return accountConverter.toDto(user);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to convert User to AccountResponseDto");
                    }
                })
                .collect(Collectors.toList());
    }

}
