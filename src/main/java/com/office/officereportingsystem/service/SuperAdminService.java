package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.AccountUpdateRequestDto;
import com.office.officereportingsystem.dto.response.AccountResponseDto;

import java.io.IOException;
import java.util.List;

public interface SuperAdminService {
    AccountUpdateRequestDto getAdminById(Integer id) throws IOException;
    List<AccountResponseDto> getAllAdmins();
}
