package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.AccountCreateRequestDto;
import com.office.officereportingsystem.dto.request.AccountUpdateRequestDto;
import com.office.officereportingsystem.dto.response.AccountResponseDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    AccountUpdateRequestDto getUserById(Integer id) throws IOException;
    List<AccountResponseDto> getAllUsers();
}
