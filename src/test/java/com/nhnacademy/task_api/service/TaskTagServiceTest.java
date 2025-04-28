package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.exception.TagNotFoundException;
import com.nhnacademy.task_api.domain.exception.TaskNotFoundException;
import com.nhnacademy.task_api.domain.exception.TaskTagNotFoundException;
import com.nhnacademy.task_api.domain.model.*;
import com.nhnacademy.task_api.repository.ProjectRepository;
import com.nhnacademy.task_api.repository.TagRepository;
import com.nhnacademy.task_api.repository.TaskRepository;
import com.nhnacademy.task_api.repository.TaskTagRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class TaskTagServiceTest {
    @Autowired
    TaskTagService taskTagService;
    @MockitoBean
    TaskTagRepository taskTagRepository;
    @MockitoBean
    TaskRepository taskRepository;
    @MockitoBean
    TagRepository tagRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private Project project;
    private Task task;
    private Tag tag;
    private TaskTag taskTag;

    @BeforeEach
    void setUp() {
        project = new Project("Project A", "user1", Status.ACTIVE);
        projectRepository.save(project);

        task = new Task("Task A", "user1", "user2", project);
        taskRepository.save(task);

        tag = new Tag("tag name");
        tag.setProject(project);
        tagRepository.save(tag);

        taskTag = new TaskTag(task, tag);
    }

    @Test
    @DisplayName("save tasktag - success")
    void save_tasktag_success_test() {
        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.existsById(anyLong())).thenReturn(true);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
        taskTagService.saveTaskTag(1L, 1L);

        verify(taskTagRepository, times(1)).save(any(TaskTag.class));
    }

    @Test
    @DisplayName("save tasktag - fail1")
    void save_tasktag_fail1_test() {
        when(taskRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> taskTagService.saveTaskTag(10L, 1L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    @DisplayName("save tasktag - fail2")
    void save_tasktag_fail2_test() {
        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> taskTagService.saveTaskTag(1L, 10L))
                .isInstanceOf(TagNotFoundException.class);
    }

    @Test
    @DisplayName("find tasktags by task - success")
    void find_tasktags_success_test() {
        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(taskTagRepository.findAllByTask_TaskId(anyLong())).thenReturn(List.of(taskTag));
        List<Tag> tags = taskTagService.findTagsByTask(1L);

        assertThat(tags).hasSize(1);
        assertThat(tags.getFirst()).isEqualTo(tag);
    }

    @Test
    @DisplayName("find tasktags by task - fail")
    void find_tasktags_fail_test() {
        when(taskRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> taskTagService.findTagsByTask(10L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    @DisplayName("delete tasktag - success")
    void delete_tasktag_success_test() {
        when(taskTagRepository.existsById(any(TaskTagPk.class))).thenReturn(true);
        taskTagService.deleteTaskTag(1L, 1L);

        verify(taskTagRepository, times(1)).deleteById(any(TaskTagPk.class));
    }

    @Test
    @DisplayName("delete tasktag - fail")
    void delete_tasktag_fail_test() {
        when(taskTagRepository.existsById(any(TaskTagPk.class))).thenReturn(false);

        assertThatThrownBy(() -> taskTagService.deleteTaskTag(1L, 1L))
                .isInstanceOf(TaskTagNotFoundException.class);
    }
}
