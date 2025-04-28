package com.nhnacademy.task_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.task_api.domain.dto.CommentRequest;
import com.nhnacademy.task_api.domain.model.Comment;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Status;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Task 내에 있는 Comment 리스트 조회")
    void get_find_all_comments_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task = new Task("Task A", "user1", "user2", project);
        Comment comment1 = new Comment("user1", "comment test1", task);
        Comment comment2 = new Comment("user2", "comment test2", task);
        List<Comment> comments = List.of(comment1, comment2);

        when(commentService.findComments(anyLong())).thenReturn(comments);

        mockMvc.perform(get("/1/1/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments").isArray())
                .andExpect(jsonPath("$.comments[0].content").value("comment test1"))
                .andExpect(jsonPath("$.comments[1].content").value("comment test2"));
    }

    @Test
    @DisplayName("Task 내에 Comment 생성")
    void post_save_comment_test() throws Exception {
        CommentRequest request = new CommentRequest();
        request.setWriterId("new writer 1");
        request.setContent("new content 1");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/1/1/")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Comment saved"));
    }

    @Test
    @DisplayName("Comment 수정")
    void put_update_comment_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task = new Task("Task A", "user1", "user2", project);
        Comment comment = new Comment("user1", "comment test1", task);
        commentService.saveComment(comment, 1L);

        CommentRequest request = new CommentRequest();
        request.setWriterId("new writer 1");
        request.setContent("update content 1");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/1/1/1")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Comment updated"));
    }
    
    @Test
    @DisplayName("Comment 삭제")
    void delete_delete_comment_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        Task task = new Task("Task A", "user1", "user2", project);
        Comment comment = new Comment("user1", "comment test1", task);
        commentService.saveComment(comment, 1L);

        mockMvc.perform(delete("/1/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Comment deleted"));
    }
}
