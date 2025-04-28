package com.nhnacademy.task_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    private Long taskId;
    private Long projectId;
    private Long mileStoneId;
    private String taskName;
    private String creatorId;
    private String managerId;
    private LocalDateTime createdAt;
}