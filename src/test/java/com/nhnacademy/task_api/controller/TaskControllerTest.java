package com.nhnacademy.task_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.task_api.domain.dto.TaskRequest;
import com.nhnacademy.task_api.domain.model.*;
import com.nhnacademy.task_api.service.MileStoneService;
import com.nhnacademy.task_api.service.TaskService;
import com.nhnacademy.task_api.service.TaskTagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        mockMvc.perform(get("/1/")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks[0].taskName").value("Task A"))
                .andExpect(jsonPath("$.tasks[1].taskName").value("Task B"));
    }

    @Test
    @DisplayName("Project 내에 새로운 Task 생성")
    void post_save_task_test() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setTaskName("new Task");
        request.setUserId("user1");
        request.setManagerId("manager1");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/1/")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("userId", "user1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Task saved"));
    }
    
    @Test
    @DisplayName("Project 내에 있는 단일 Task 조회")
    void get_find_tasks_by_id_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task = new Task("Task A", "user1", "manager1", project);
        when(taskService.findTaskById(anyLong())).thenReturn(task);

        mockMvc.perform(get("/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskName").value("Task A"))
                .andExpect(jsonPath("$.managerId").value("manager1"));
    }

    @Test
    @DisplayName("Project 내에 있는 Task 수정")
    void put_update_task_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task = new Task("Task A", "user1", "manager1", project);
        taskService.saveTask(task, 1L);

        TaskRequest request = new TaskRequest();
        request.setTaskName("update Task");
        request.setUserId("user1");
        request.setManagerId("manager2");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/1/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Task updated"));
    }

    @Test
    @DisplayName("Project 내에 있는 Task 삭제")
    void delete_delete_task_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task = new Task("Task A", "user1", "manager1", project);
        taskService.saveTask(task, 1L);

        mockMvc.perform(delete("/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Task deleted"));
    }

    @Test
    @DisplayName("Task에 설정 되어있는 Tag 리스트 조회")
    void get_find_all_tags_in_task_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task = new Task("Task A", "user1", "manager1", project);
        Tag tag1 = new Tag("tag A");
        tag1.setProject(project);
        Tag tag2 = new Tag("tag B");
        tag2.setProject(project);

        List<Tag> tags = List.of(tag1, tag2);
        when(taskTagService.findTagsByTask(1L)).thenReturn(tags);

        mockMvc.perform(get("/1/1/tag"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags[0].tagName").value("tag A"))
                .andExpect(jsonPath("$.tags[1].tagName").value("tag B"));
    }

    @Test
    @DisplayName("Task에 Tag 등록")
    void post_save_tag_in_task_test() throws Exception {
        mockMvc.perform(post("/1/1/tag/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Task tag saved"));
    }

    @Test
    @DisplayName("Task에 Tag 삭제")
    void delete_delete_tag_in_task_test() throws Exception {
        mockMvc.perform(delete("/1/1/tag/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Task tag deleted"));
    }

    @Test
    @DisplayName("Task에 설정 되어있는 MileStone 조회")
    void get_find_milestone_in_task_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task = new Task("Task A", "user1", "manager1", project);
        MileStone mileStone = new MileStone("milestone A", LocalDate.parse("2025-02-01"), LocalDate.parse("2025-02-10"));
        mileStone.setProject(project);
        task.setMileStone(mileStone);

        when(taskService.findTaskById(anyLong())).thenReturn(task);

        mockMvc.perform(get("/1/1/milestone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.milestone.milestoneName").value("milestone A"));
    }

    @Test
    @DisplayName("Task에 MileStone 등록")
    void post_save_milestone_in_task_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task = new Task("Task A", "user1", "manager1", project);
        MileStone mileStone = new MileStone("milestone A", LocalDate.parse("2025-02-01"), LocalDate.parse("2025-02-10"));
        mileStone.setProject(project);
        mileStoneService.saveMileStone(mileStone, 1L);

        mockMvc.perform(post("/1/1/milestone/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("MileStone save in task"));
    }

    @Test
    @DisplayName("Task에 MileStone 삭제")
    void delete_delete_milestone_in_task_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task = new Task("Task A", "user1", "manager1", project);
        MileStone mileStone = new MileStone("milestone A", LocalDate.parse("2025-02-01"), LocalDate.parse("2025-02-10"));
        mileStone.setProject(project);
        task.setMileStone(mileStone);

        mockMvc.perform(delete("/1/1/milestone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("MileStone delete in task"));
    }
}
