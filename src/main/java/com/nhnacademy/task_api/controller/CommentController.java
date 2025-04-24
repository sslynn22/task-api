package com.nhnacademy.task_api.controller;

import com.nhnacademy.task_api.domain.dto.CommentDTO;
import com.nhnacademy.task_api.domain.dto.CommentRequest;
import com.nhnacademy.task_api.domain.dto.ResponseDTO;
import com.nhnacademy.task_api.domain.model.Comment;
import com.nhnacademy.task_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{projectId}/{taskId}")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/")
    public List<Comment> findAllTasks(@PathVariable("taskId") long taskId) {
//        return new CommentDTO(commentService.findComments(taskId));
        return commentService.findComments(taskId);
    }

    @PostMapping("/")
    public ResponseDTO saveTask(@PathVariable("taskId") long taskId,
                                @RequestBody CommentRequest request) {
        commentService.saveComment(request.makeComment(), taskId);
        return new ResponseDTO(HttpStatus.OK, "Comment saved");
    }

    @PutMapping("/{commentId}")
    public ResponseDTO updateTask(@PathVariable("commentId") long commentId,
                                  @RequestBody CommentRequest request) {
        commentService.updateComment(commentId, request);
        return new ResponseDTO(HttpStatus.OK, "Comment updated");
    }

    @DeleteMapping("/{commentId}")
    public ResponseDTO deleteTask(@PathVariable("commentId") long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseDTO(HttpStatus.OK, "Comment deleted");
    }
}
