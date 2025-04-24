package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTask_TaskId(Long taskId);
}
