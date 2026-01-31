package com.office.officereportingsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyReportRequestDto {

    @NotNull(message = "Task is required")
    private Integer taskId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Hours worked is required")
    @Positive(message = "Hours worked must be positive")
    private Double hoursWorked;
}
