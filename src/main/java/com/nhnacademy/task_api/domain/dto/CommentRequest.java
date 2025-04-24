package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentRequest {
    private String writerId;
    private String content;

    public Comment makeComment() {
        return new Comment(writerId, content);
    }

    public void applyTo(Comment comment) {
        if(writerId != null) {
            comment.setWriterId(writerId);
        }
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
    }
}
