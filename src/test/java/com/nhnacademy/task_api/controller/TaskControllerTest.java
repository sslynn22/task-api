package com.nhnacademy.task_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Status;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.service.MileStoneService;
import com.nhnacademy.task_api.service.TaskService;
import com.nhnacademy.task_api.service.TaskTagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TaskService taskService;
    @MockitoBean
    private TaskTagService taskTagService;
    @MockitoBean
    private MileStoneService mileStoneService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Project 내에 있는 Task 목록 조회")
    void get_find_all_tasks_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task1 = new Task("Task A", "user1", "user2", project);
        Task task2 = new Task("Task B", "user1", "user3", project);
        List<Task> tasks = List.of(task1, task2);

        when(taskService.findTasks(anyLong())).thenReturn(tasks);

        // mockMvc...
    }
}
