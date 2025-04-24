package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private String taskName;
    private String managerId;

    public Task makeTask() {
        Task task = new Task(this.taskName, this.managerId);
        return task;
    }
}