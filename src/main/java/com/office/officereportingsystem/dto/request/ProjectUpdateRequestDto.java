package com.office.officereportingsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProjectUpdateRequestDto {
    private String projectName;
    private String description;
}
