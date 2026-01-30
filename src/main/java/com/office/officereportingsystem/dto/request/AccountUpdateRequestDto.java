package com.office.officereportingsystem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountUpdateRequestDto {

    @Size(max = 20, message = "FIRST_NAME_MAX_LENGTH")
    private String firstName;

    @Size(max = 20, message = "MIDDLE_NAME_MAX_LENGTH")
    private String middleName;

    @Size(max = 20, message = "LAST_NAME_MAX_LENGTH")
    private String lastName;

    @Email(message = "EMAIL_INVALID")
    @Size(max = 100, message = "EMAIL_MAX_LENGTH")
    private String email;
}
