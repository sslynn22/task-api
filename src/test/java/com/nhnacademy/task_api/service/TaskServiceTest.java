package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.dto.TaskRequest;
import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
import com.nhnacademy.task_api.domain.exception.TaskNotFoundException;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Status;
import com.nhnacademy.task_api.domain.model.Task;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;
    @MockitoBean
    private TaskRepository taskRepository;
    @MockitoBean
    private ProjectRepository projectRepository;

    private Project project;
    private Task task;

    @BeforeEach
    void setUp() {
        project = new Project("Project A", "user1", Status.ACTIVE);
        projectRepository.save(project);
        task = new Task("Task A", "user1", "user2", project);
    }

    @Test
    @DisplayName("save task")
    void save_task_test() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        taskService.saveTask(task, 1L);

        verify(taskRepository, times(1)).save(task);
    }

    @Test
    @DisplayName("find tasks - success")
    void find_tasks_success_test() {
        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(taskRepository.findAllByProject_ProjectId(anyLong())).thenReturn(List.of(task));
        List<Task> tasks = taskService.findTasks(1L);

        assertThat(tasks).hasSize(1);
        assertThat(tasks.getFirst()).isEqualTo(task);
    }

    @Test
    @DisplayName("find tasks - fail")
    void find_tasks_fail_test() {
        when(projectRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> taskService.findTasks(10L))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    @DisplayName("find task by id - success")
    void find_task_by_id_success_test() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        Task findTask = taskService.findTaskById(1L);

        assertThat(findTask).isNotNull();
        assertThat(findTask).isEqualTo(task);
    }

    @Test
    @DisplayName("find task by id - fail")
    void find_task_by_id_fail_test() {
        when(taskRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findTaskById(10L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    @DisplayName("update task - success")
    void update_task_success_test() {
        TaskRequest request = new TaskRequest();
        request.setTaskName("new task name");
        request.setUserId("user1");
        request.setManagerId("manager1");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        taskService.updateTask(1L, request);

        assertThat(task.getTaskName()).isEqualTo("new task name");
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    @DisplayName("update task - fail1")
    void update_task_fail1_test() {
        assertThatThrownBy(() -> taskService.updateTask(1L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("update task - fail2")
    void update_task_fail2_test() {
        TaskRequest request = new TaskRequest();
        request.setTaskName("new task name");
        request.setUserId("user1");
        request.setManagerId("manager1");

        when(taskRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.updateTask(10L, request))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    @DisplayName("delete task - success")
    void delete_task_success_test() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete task - fail")
    void delete_task_fail_test() {
        when(taskRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> taskService.deleteTask(10L))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
