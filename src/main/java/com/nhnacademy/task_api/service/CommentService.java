package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.dto.CommentRequest;
import com.nhnacademy.task_api.domain.model.Comment;

import java.util.List;

public interface CommentService {
    void saveComment(Comment comment, long taskId);
    List<Comment> findComments(long taskId);
    void updateComment(long commentId, CommentRequest request);
    void deleteComment(long commendId);
}