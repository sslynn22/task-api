package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.dto.CommentRequest;
import com.nhnacademy.task_api.domain.exception.CommentNotFoundException;
import com.nhnacademy.task_api.domain.exception.TaskNotFoundException;
import com.nhnacademy.task_api.domain.model.Comment;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Status;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.repository.CommentRepository;
import com.nhnacademy.task_api.repository.ProjectRepository;
import com.nhnacademy.task_api.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
@Transactional
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @MockitoBean
    private TaskRepository taskRepository;
    @MockitoBean
    private CommentRepository commentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private Project project;
    private Task task;
    private Comment comment;

    @BeforeEach
    void setUp() {
        project = new Project("Project A", "user1", Status.ACTIVE);
        projectRepository.save(project);
        task = new Task("Task A", "user1", "user2", project);
        taskRepository.save(task);
        comment = new Comment("user1", "comment test1", task);
    }

    @Test
    @DisplayName("save comment")
    void save_comment_test() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        commentService.saveComment(comment, 1L);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    @DisplayName("find comments - success")
    void find_comments_success_test() {
        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(commentRepository.findAllByTask_TaskId(anyLong())).thenReturn(List.of(comment));
        List<Comment> comments = commentService.findComments(1L);

        assertThat(comments).hasSize(1);
        assertThat(comments.getFirst()).isEqualTo(comment);
    }

    @Test
    @DisplayName("find comments - fail")
    void find_comments_fail_test() {
        when(taskRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> commentService.findComments(10L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    @DisplayName("update comment - success")
    void update_comment_success_test() {
        CommentRequest request = new CommentRequest();
        request.setWriterId("user1");
        request.setContent("new comment content");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        commentService.updateComment(1L, request);

        assertThat(comment.getContent()).isEqualTo("new comment content");
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    @DisplayName("update comment - fail1")
    void update_comment_fail1_test() {
        assertThatThrownBy(() -> commentService.updateComment(10L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("update comment - fail2")
    void update_comment_fail2_test() {
        CommentRequest request = new CommentRequest();
        request.setWriterId("user1");
        request.setContent("new comment content");

        when(commentRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.updateComment(10L, request))
                .isInstanceOf(CommentNotFoundException.class);
    }

    @Test
    @DisplayName("delete comment - success")
    void delete_comment_success_test() {
        when(commentRepository.existsById(1L)).thenReturn(true);
        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete comment - fail")
    void delete_comment_fail_test() {
        when(commentRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> commentService.deleteComment(10L))
                .isInstanceOf(CommentNotFoundException.class);
    }
}
