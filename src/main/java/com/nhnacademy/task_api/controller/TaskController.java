package com.nhnacademy.task_api.controller;

import com.nhnacademy.task_api.domain.dto.*;
import com.nhnacademy.task_api.domain.model.MileStone;
import com.nhnacademy.task_api.domain.model.Tag;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.service.MileStoneService;
import com.nhnacademy.task_api.service.TaskService;
import com.nhnacademy.task_api.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{projectId}")
public class TaskController {
    private final TaskService taskService;
    private final TaskTagService taskTagService;
    private final MileStoneService mileStoneService;

    @GetMapping("/")
    public ResponseEntity<TaskListDTO> findAllTasks(@PathVariable("projectId") long projectId) {
        List<Task> tasks = taskService.findTasks(projectId);
        TaskListDTO taskListDTO = new TaskListDTO(tasks);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskListDTO);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDTO> saveTask(@PathVariable("projectId") long projectId,
                                                @RequestBody TaskRequest request,
                                                @RequestParam String userId) {
        request.setUserId(userId);  // set : 현재 로그인 된 사용자 id
        taskService.saveTask(request.makeTask(), projectId);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Task saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> findTaskById(@PathVariable("taskId") long taskId) {
        Task task = taskService.findTaskById(taskId);
        TaskDTO taskDTO = new TaskDTO(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskDTO);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseDTO> updateTask(@PathVariable("taskId") long taskId,
                                                  @RequestBody TaskRequest request) {
        taskService.updateTask(taskId, request);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CREATED, "Task updated");;

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseDTO> deleteTask(@PathVariable("taskId") long taskId) {
        taskService.deleteTask(taskId);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Task deleted");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{taskId}/tag")
    public ResponseEntity<TagListDTO> findAllTagsInTask(@PathVariable("taskId") long taskId) {
        List<Tag> tags = taskTagService.findTagsByTask(taskId);
        TagListDTO tagListDTO = new TagListDTO(tags);

        return ResponseEntity.status(HttpStatus.CREATED).body(tagListDTO);
    }

    @PostMapping("/{taskId}/tag/{tagId}")
    public ResponseEntity<ResponseDTO> saveTagInTask(@PathVariable("taskId") long taskId,
                                                     @PathVariable("tagId") long tagId) {
        taskTagService.saveTaskTag(taskId, tagId);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Task tag saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{taskId}/tag/{tagId}")
    public ResponseEntity<ResponseDTO> deleteTagInTask(@PathVariable("taskId") long taskId,
                                                       @PathVariable("tagId") long tagId) {
        taskTagService.deleteTaskTag(taskId, tagId);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Task tag deleted");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{taskId}/milestone")
    public ResponseEntity<MileStoneDTO> findMileStoneInTask(@PathVariable("taskId") long taskId) {
        MileStone mileStone = taskService.findTaskById(taskId).getMileStone();
        MileStoneDTO mileStoneDTO = new MileStoneDTO(mileStone);

        return ResponseEntity.status(HttpStatus.CREATED).body(mileStoneDTO);
    }

    @PostMapping("/{taskId}/milestone/{milestoneId}")
    public ResponseEntity<ResponseDTO> saveMileStoneInTask(@PathVariable("taskId") long taskId,
                                                           @PathVariable("milestoneId") long milestoneId) {
        Task task = taskService.findTaskById(taskId);
        MileStone mileStone = mileStoneService.findMileStoneById(milestoneId);
        task.setMileStone(mileStone);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "MileStone save in task");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{taskId}/milestone")
    public ResponseEntity<ResponseDTO> deleteMileStoneInTask(@PathVariable("taskId") long taskId) {
        Task task = taskService.findTaskById(taskId);
        task.setMileStone(null);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "MileStone delete in task");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}