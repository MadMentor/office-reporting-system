package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.AdminCreateRequestDto;

public interface SuperAdminService {
    void createAdmin(AdminCreateRequestDto adminCreateRequestDto);
}
