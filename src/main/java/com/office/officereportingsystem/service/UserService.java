package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.UserCreateRequestDtos;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.exception.UserAlreadyExistsException;

public interface UserService {
    /**
     * Create a new user in the system.
     * Steps:
     * 1. Normalize names and email
     * 2. Validate email uniqueness
     * 3. Encrypt password using BCrypt
     * 4. Set createdAt timestamp
     *
     * @param userCreateRequestDto DTO with user info
     * @return saved User entity
     * @throws UserAlreadyExistsException if email is already used
     */
    User createUser(UserCreateRequestDtos userCreateRequestDto);
}
