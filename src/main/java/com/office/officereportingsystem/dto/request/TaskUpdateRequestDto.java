package com.office.officereportingsystem.dto.request;

import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class TaskUpdateRequestDto {

    private String title;
    private String description;
    private Integer assignedToId;
    private LocalDateTime dueDate;
    private Integer projectId;

}
