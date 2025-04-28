package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentListDTO {
    List<Comment> comments;
}
