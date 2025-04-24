package com.nhnacademy.task_api.controller;

import com.nhnacademy.task_api.domain.dto.ResponseDTO;
import com.nhnacademy.task_api.domain.dto.TaskDTO;
import com.nhnacademy.task_api.domain.dto.TaskRequest;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{projectId}")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/")
    public List<Task> findAllTasks(@PathVariable("projectId") long projectId) {
//        return new TaskDTO(taskService.findTasks(projectId));
        return taskService.findTasks(projectId);
    }

    @PostMapping("/")
    public ResponseDTO saveTask(@PathVariable("projectId") long projectId,
                                @RequestBody TaskRequest request) {
        taskService.saveTask(request.makeTask(), projectId);
        return new ResponseDTO(HttpStatus.OK, "Task saved");
    }

    @GetMapping("/{taskId}")
    public Task findTaskById(@PathVariable("taskId") long taskId) {
//        return new TaskDTO(taskService.findTaskById(taskId));
        return taskService.findTaskById(taskId);
    }

    @PutMapping("/{taskId}")
    public ResponseDTO updateTask(@PathVariable("taskId") long taskId,
                                  @RequestBody TaskRequest request) {
        taskService.updateTask(taskId, request);
        return new ResponseDTO(HttpStatus.OK, "Task updated");
    }

    @DeleteMapping("/{taskId}")
    public ResponseDTO deleteTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseDTO(HttpStatus.OK, "Task deleted");
    }
}