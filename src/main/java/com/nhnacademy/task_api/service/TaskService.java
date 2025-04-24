package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.model.Task;

import java.util.List;

public interface TaskService {
    void saveTask(Task task, long projectId);
    List<Task> findTasks(long projectId);
    Task findTaskById(Long taskId);
    void updateTask(Task task);
    void deleteTask(Long taskId);
}
