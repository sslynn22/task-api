package com.nhnacademy.task_api.controller;

import com.nhnacademy.task_api.domain.dto.CommentListDTO;
import com.nhnacademy.task_api.domain.dto.CommentRequest;
import com.nhnacademy.task_api.domain.dto.ResponseDTO;
import com.nhnacademy.task_api.domain.model.Comment;
import com.nhnacademy.task_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{projectId}/{taskId}")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<CommentListDTO> findAllComments(@PathVariable("taskId") long taskId) {
        List<Comment> comments = commentService.findComments(taskId);
        CommentListDTO commentListDTO = new CommentListDTO(comments);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentListDTO);
    }

    // Task 내에 Comment 생성
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> saveComment(@PathVariable("taskId") long taskId,
                                                   @RequestBody CommentRequest request) {
        commentService.saveComment(request.makeComment(), taskId);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CREATED, "Comment saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Comment 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDTO> updateComment(@PathVariable("commentId") long commentId,
                                  @RequestBody CommentRequest request) {
        commentService.updateComment(commentId, request);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.CREATED, "Comment updated");

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Comment 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDTO> deleteComment(@PathVariable("commentId") long commentId) {
        commentService.deleteComment(commentId);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "Comment deleted");

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
