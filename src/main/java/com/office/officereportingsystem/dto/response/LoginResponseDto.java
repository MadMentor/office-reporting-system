package com.office.officereportingsystem.dto.response;

import com.office.officereportingsystem.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String email;
    private Role role;
    private String messageCode;
}
