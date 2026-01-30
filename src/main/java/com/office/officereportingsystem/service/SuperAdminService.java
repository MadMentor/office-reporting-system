package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.AdminCreateRequestDto;
import com.office.officereportingsystem.dto.request.AdminUpdateRequestDto;
import com.office.officereportingsystem.dto.response.AdminResponseDto;

import java.io.IOException;
import java.util.List;

public interface SuperAdminService {
    AdminUpdateRequestDto getAdminById(Integer id) throws IOException;
    void createAdmin(AdminCreateRequestDto adminCreateRequestDto);
    List<AdminResponseDto> getAllAdmins();
    void updateAdmin(Integer id, AdminUpdateRequestDto dto);
    void deleteAdmin(Integer adminId);
}
