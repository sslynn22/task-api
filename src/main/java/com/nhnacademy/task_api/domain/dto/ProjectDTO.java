package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectDTO {
    private Long projectId;
    private String projectName;
    private LocalDateTime createdAt;
    private String adminId;
    private Status projectStatus;
}
