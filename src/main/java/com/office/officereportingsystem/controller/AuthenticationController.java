package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.dto.request.LoginRequestDto;
import com.office.officereportingsystem.dto.response.LoginResponseDto;
import com.office.officereportingsystem.dto.response.ResponseDto;
import com.office.officereportingsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponse = authService.login(loginRequestDto);

        return success(loginResponse, loginResponse.getMessageCode());
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout() {
        return success("LOGOUT_SUCCESS");
    }
}
