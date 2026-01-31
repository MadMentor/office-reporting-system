package com.office.officereportingsystem.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskCreateRequestDto {
    @NotBlank(message = "TASK_TITLE_REQUIRED")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @Size(max = 1000, message = "Description must be at most 500 characters")
    private String description;

    @NotNull(message = "TASK_DUE_DATE_REQUIRED")
    @Future(message = "DUE_DATE_MUS_BE_IN_FUTURE")
    private LocalDateTime dueDate;

    @NotNull(message = "User must be assigned")
    private Integer assignedToId;

    @NotNull(message = "Project must be selected")
    private Integer projectId;
}
