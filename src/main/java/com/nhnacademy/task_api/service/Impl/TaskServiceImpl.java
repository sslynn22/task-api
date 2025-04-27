package com.nhnacademy.task_api.service.Impl;

import com.nhnacademy.task_api.domain.dto.TaskRequest;
import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
import com.nhnacademy.task_api.domain.exception.TaskNotFoundException;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.repository.MileStoneRepository;
import com.nhnacademy.task_api.repository.ProjectRepository;
import com.nhnacademy.task_api.repository.TaskRepository;
import com.nhnacademy.task_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MileStoneRepository mileStoneRepository;

    @Override
    public void saveTask(Task task, long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException());
        task.setProject(project);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findTasks(long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException();
        }
        return taskRepository.findAllByProject_ProjectId(projectId);
    }

    @Override
    public Task findTaskById(long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException());
    }

    @Override
    public void updateTask(long taskId, TaskRequest request) {
        if(Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        Optional<Task> task = taskRepository.findById(taskId);
        if(task.isEmpty()) {
            throw new TaskNotFoundException();
        }
        request.applyTo(task.get());
        taskRepository.save(task.get());
    }

    @Override
    public void deleteTask(long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException();
        }
        taskRepository.deleteById(taskId);
    }
}