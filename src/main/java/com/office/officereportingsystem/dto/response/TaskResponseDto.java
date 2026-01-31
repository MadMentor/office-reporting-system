package com.office.officereportingsystem.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponseDto {
    private Integer id;
    private String title;
    private String description;
    private Integer assignedTo;
    private Integer assignedBy;
    private String projectName;
    private LocalDateTime dueDate;
}
