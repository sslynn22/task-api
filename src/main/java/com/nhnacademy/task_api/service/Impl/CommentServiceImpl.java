package com.nhnacademy.task_api.service.Impl;

import com.nhnacademy.task_api.domain.dto.CommentRequest;
import com.nhnacademy.task_api.domain.exception.CommentNotFoundException;
import com.nhnacademy.task_api.domain.exception.TaskNotFoundException;
import com.nhnacademy.task_api.domain.model.Comment;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.repository.CommentRepository;
import com.nhnacademy.task_api.repository.TaskRepository;
import com.nhnacademy.task_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    @Override
    public void saveComment(Comment comment, long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException());
        comment.setTask(task);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findComments(long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException();
        }
        return commentRepository.findAllByTask_TaskId(taskId);
    }

    @Override
    public void updateComment(long commentId, CommentRequest request) {
        if(Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()) {
            throw new CommentNotFoundException();
        }
        request.applyTo(comment.get());
        commentRepository.save(comment.get());
    }

    @Override
    public void deleteComment(long commendId) {
        if (!commentRepository.existsById(commendId)) {
            throw new CommentNotFoundException();
        }
        commentRepository.deleteById(commendId);
    }
}
