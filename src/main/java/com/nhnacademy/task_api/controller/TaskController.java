package com.nhnacademy.task_api.controller;

import com.nhnacademy.task_api.domain.dto.*;
import com.nhnacademy.task_api.domain.model.Comment;
import com.nhnacademy.task_api.domain.model.MileStone;
import com.nhnacademy.task_api.domain.model.Tag;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.service.CommentService;
import com.nhnacademy.task_api.service.MileStoneService;
import com.nhnacademy.task_api.service.TaskService;
import com.nhnacademy.task_api.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{projectId}")
public class TaskController {
    private final TaskService taskService;
    private final TaskTagService taskTagService;
    private final MileStoneService mileStoneService;
    private final CommentService commentService;

    // Project 내에 있는 Task 목록 조회
    @GetMapping("/")
    public ResponseEntity<TaskListDTO> findAllTasks(@PathVariable("projectId") long projectId) {
        List<Task> tasks = taskService.findTasks(projectId);
        TaskListDTO taskListDTO = new TaskListDTO(tasks);

        return ResponseEntity.status(HttpStatus.OK).body(taskListDTO);
    }

    // Project 내에 새로운 Task 생성
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> saveTask(@PathVariable("projectId") long projectId,
                                                @RequestBody TaskRequest request,
                                                @RequestParam String userId) {
        request.setUserId(userId);  // set : 현재 로그인 된 사용자 id
        taskService.saveTask(request.makeTask(), projectId);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Task saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Project 내에 있는 단일 Task 조회
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> findTaskById(@PathVariable("taskId") long taskId) {
        Task task = taskService.findTaskById(taskId);
        List<Comment> comments = commentService.findComments(taskId);
        TaskDTO taskDTO = new TaskDTO(
                task.getTaskId(),
                task.getProject().getProjectId(),
                Objects.isNull(task.getMileStone())? null : task.getMileStone().getMilestoneId(),
                task.getTaskName(),
                task.getUserId(),
                task.getManagerId(),
                task.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
    }

    // Project 내에 있는 Task 수정
    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseDTO> updateTask(@PathVariable("taskId") long taskId,
                                                  @RequestBody TaskRequest request) {
        taskService.updateTask(taskId, request);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CREATED, "Task updated");;

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Project 내에 있는 Task 삭제
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseDTO> deleteTask(@PathVariable("taskId") long taskId) {
        taskService.deleteTask(taskId);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "Task deleted");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Task에 설정 되어있는 Tag 리스트 조회
    @GetMapping("/{taskId}/tag")
    public ResponseEntity<TagListDTO> findAllTagsInTask(@PathVariable("taskId") long taskId) {
        List<Tag> setTags = taskTagService.findTagsByTask(taskId);
        TagListDTO tagListDTO = new TagListDTO(setTags);

        return ResponseEntity.status(HttpStatus.OK).body(tagListDTO);
    }

    // Task에 Tag 등록
    @PostMapping("/{taskId}/tag/{tagId}")
    public ResponseEntity<ResponseDTO> saveTagInTask(@PathVariable("taskId") long taskId,
                                                     @PathVariable("tagId") long tagId) {
        taskTagService.saveTaskTag(taskId, tagId);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Task tag saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Task에 Tag 삭제
    @DeleteMapping("/{taskId}/tag/{tagId}")
    public ResponseEntity<ResponseDTO> deleteTagInTask(@PathVariable("taskId") long taskId,
                                                       @PathVariable("tagId") long tagId) {
        taskTagService.deleteTaskTag(taskId, tagId);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "Task tag deleted");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Task에 설정 되어있는 MileStone 조회
    @GetMapping("/{taskId}/milestone")
    public ResponseEntity<MileStoneDTO> findMileStoneInTask(@PathVariable("taskId") long taskId) {
        MileStone mileStone = taskService.findTaskById(taskId).getMileStone();
        MileStoneDTO mileStoneDTO = new MileStoneDTO(mileStone);

        return ResponseEntity.status(HttpStatus.OK).body(mileStoneDTO);
    }

    // Task에 MileStone 등록
    @PostMapping("/{taskId}/milestone/{milestoneId}")
    public ResponseEntity<ResponseDTO> saveMileStoneInTask(@PathVariable("taskId") long taskId,
                                                           @PathVariable("milestoneId") long milestoneId) {
        mileStoneService.setMileStone(taskId, milestoneId);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "MileStone save in task");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Task에 MileStone 삭제
    @DeleteMapping("/{taskId}/milestone")
    public ResponseEntity<ResponseDTO> deleteMileStoneInTask(@PathVariable("taskId") long taskId) {
        mileStoneService.setNullMileStone(taskId);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "MileStone delete in task");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}