package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
