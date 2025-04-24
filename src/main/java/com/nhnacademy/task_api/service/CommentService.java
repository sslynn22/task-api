package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.model.Comment;

public interface CommentService {
    void saveComment(long taskId, Comment comment);
}