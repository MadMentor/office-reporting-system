package com.office.officereportingsystem.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponseDto {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
}
