package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.Comment;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CommentDTO {
    List<Comment> comments;
}
