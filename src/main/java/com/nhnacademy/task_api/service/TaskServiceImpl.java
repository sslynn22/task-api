package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.exception.TaskNotFoundException;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.repository.ProjectRepository;
import com.nhnacademy.task_api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void saveTask(Task task, long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new TaskNotFoundException("Project not found."));
        task.setProject(project);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findTasks(long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new TaskNotFoundException("Project not found.");
        }
        return taskRepository.findAllByProject_ProjectId(projectId);
    }

    @Override
    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));
    }

    @Override
    public void updateTask(Task task) {
        Task existing = taskRepository.findById(task.getTaskId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));
        existing.setTaskName(task.getTaskName());
        taskRepository.save(existing);
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException("Task not found.");
        }
        taskRepository.deleteById(taskId);
    }
}