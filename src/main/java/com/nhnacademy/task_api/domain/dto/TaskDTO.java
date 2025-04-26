package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    Task task;
}