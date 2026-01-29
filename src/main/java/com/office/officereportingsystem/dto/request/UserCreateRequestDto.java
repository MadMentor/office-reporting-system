package com.office.officereportingsystem.dto.request;

import com.office.officereportingsystem.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
