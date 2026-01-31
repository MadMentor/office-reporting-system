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
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public long getTotalUsersCount() {
        return userRepo.count();
    }

    @Override
    public long getAdminCount() {
        return userRepo.countByRole(Role.ADMIN);
    }
}
