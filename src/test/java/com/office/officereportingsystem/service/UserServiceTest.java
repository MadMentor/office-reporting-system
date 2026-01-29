package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.LoginRequestDto;
import com.office.officereportingsystem.dto.response.LoginResponseDto;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.enums.Role;
import com.office.officereportingsystem.exception.AuthenticationFailedException;
import com.office.officereportingsystem.repository.UserRepo;
import com.office.officereportingsystem.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ---------------------------
    // 1️⃣ Successful login test
    // ---------------------------
    @Test
    void login_ShouldReturnResponse_WhenCredentialsAreValid() {
        // Given
        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");

        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("encryptedPassword") // already encrypted
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        when(userRepo.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encryptedPassword")).thenReturn(true);

        // When
        LoginResponseDto response = authService.login(request);

        // Then
        assertNotNull(response);
        assertEquals("john.doe@example.com", response.getEmail());
        assertEquals(Role.USER, response.getRole());
        assertEquals("LOGIN_SUCCESS", response.getMessageCode());
    }

    // ---------------------------
    // 2️⃣ Invalid email test
    // ---------------------------
    @Test
    void login_ShouldThrowException_WhenEmailDoesNotExist() {
        // Given
        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("nonexistent@example.com");
        request.setPassword("password123");

        when(userRepo.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When & Then
        AuthenticationFailedException exception = assertThrows(
                AuthenticationFailedException.class,
                () -> authService.login(request)
        );
        assertEquals("INVALID_CREDENTIALS", exception.getMessage());
    }

    // ---------------------------
    // 3️⃣ Invalid password test
    // ---------------------------
    @Test
    void login_ShouldThrowException_WhenPasswordIsIncorrect() {
        // Given
        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("john.doe@example.com");
        request.setPassword("wrongPassword");

        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("encryptedPassword")
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        when(userRepo.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encryptedPassword")).thenReturn(false);

        // When & Then
        AuthenticationFailedException exception = assertThrows(
                AuthenticationFailedException.class,
                () -> authService.login(request)
        );
        assertEquals("INVALID_CREDENTIALS", exception.getMessage());
    }
}
