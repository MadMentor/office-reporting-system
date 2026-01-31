package com.office.officereportingsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectCreateRequestDto {
    @NotBlank(message = "PROJECT_NAME_REQUIRED")
    private String name;

    private String description;
}
