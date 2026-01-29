package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.LoginRequestDto;
import com.office.officereportingsystem.dto.response.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto requestDto);
}
