package com.office.officereportingsystem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCreateRequestDto {
    @NotBlank(message = "FIRST_NAME_REQUIRED")
    @Size(max = 20, message = "FIRST_NAME_MAX_LENGTH")
    private String firstName;

    @Size(max = 20, message = "MIDDLE_NAME_MAX_LENGTH")
    private String middleName;

    @NotBlank(message = "LAST_NAME_REQUIRED")
    @Size(max = 20, message = "LAST_NAME_MAX_LENGTH")
    private String lastName;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    @Size(max = 100, message = "EMAIL_MAX_LENGTH")
    private String email;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 8, max = 60, message = "PASSWORD_LENGTH_INVALID")
    private String password;
}
