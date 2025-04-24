package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String taskName;
    private String userId;
    private String managerId;

    public Task makeTask() {
        return new Task(taskName, userId, managerId);
    }

    public void applyTo(Task task) {
        task.setTaskName(taskName);
        task.setManagerId(managerId);
    }
}