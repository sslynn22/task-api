package com.nhnacademy.task_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.task_api.domain.dto.MileStoneRequest;
import com.nhnacademy.task_api.domain.dto.ProjectRequest;
import com.nhnacademy.task_api.domain.dto.TagRequest;
import com.nhnacademy.task_api.domain.model.MileStone;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Status;
import com.nhnacademy.task_api.domain.model.Tag;
import com.nhnacademy.task_api.service.MemberService;
import com.nhnacademy.task_api.service.MileStoneService;
import com.nhnacademy.task_api.service.ProjectService;
import com.nhnacademy.task_api.service.TagService;
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

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProjectService projectService;
    @MockitoBean
    private MemberService memberService;
    @MockitoBean
    private TagService tagService;
    @MockitoBean
    private MileStoneService mileStoneService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인 한 유저의 Project 목록 조회")
    void get_find_all_projects_test() throws Exception {
        Project project1 = new Project("Project A", "user1", Status.ACTIVE);
        Project project2 = new Project("Project B", "user1", Status.DORMANT);
        List<Project> projects = List.of(project1, project2);

        when(projectService.findProjects("user1")).thenReturn(projects);

        mockMvc.perform(get("/")
                        .param("adminId", "user1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projects").isArray())
                .andExpect(jsonPath("$.projects[0].projectName").value("Project A"))
                .andExpect(jsonPath("$.projects[1].projectName").value("Project B"));
    }

    @Test
    @DisplayName("새로운 Project 생성")
    void post_save_project_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        projectService.saveProject(project);

        ProjectRequest request = new ProjectRequest();
        request.setProjectName("Project A");
        request.setAdminId("user1");
        request.setProjectStatus(Status.ACTIVE);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Project saved"));
    }

    @Test
    @DisplayName("Project 단일 조회")
    void get_find_project_by_id_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        when(projectService.findProjectById(anyLong())).thenReturn(project);

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName").value("Project A"))
                .andExpect(jsonPath("$.adminId").value("user1"))
                .andExpect(jsonPath("$.projectStatus").value("ACTIVE"));
    }

    @Test
    @DisplayName("Project 수정")
    void put_update_project_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        projectService.saveProject(project);

        ProjectRequest request = new ProjectRequest();
        request.setProjectName("Project A Update");
        request.setProjectStatus(Status.COMPLETED);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/1")
                        .param("adminId", "user1")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Project updated"));
    }

    @Test
    @DisplayName("Project 삭제")
    void delete_delete_project_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        projectService.saveProject(project);

        mockMvc.perform(delete("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Project deleted"));
    }

    @Test
    @DisplayName("Project에 속한 멤버 리스트 조회")
    void get_find_all_members_test() throws Exception {
        List<String> memberIds = List.of("user2", "user3");
        when(memberService.findMemberIds(anyLong())).thenReturn(memberIds);

        mockMvc.perform(get("/1/member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberIds").isArray())
                .andExpect(jsonPath("$.memberIds[0]").value("user2"))
                .andExpect(jsonPath("$.memberIds[1]").value("user3"));
    }

    @Test
    @DisplayName("Project에 새로운 멤버 추가")
    void post_save_member_test() throws Exception {
        mockMvc.perform(post("/1/member/user4"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Member saved"));
    }

    @Test
    @DisplayName("Project에 속한 멤버 삭제")
    void delete_delete_member_test() throws Exception {
        mockMvc.perform(delete("/1/member/user4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Member deleted"));
    }

    @Test
    @DisplayName("Project Tag 리스트 조회")
    void get_find_all_tags_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        projectService.saveProject(project);

        Tag tag1 = new Tag("tag A");
        tag1.setProject(project);
        Tag tag2 = new Tag("tag B");
        tag2.setProject(project);

        List<Tag> tags = List.of(tag1, tag2);
        when(tagService.findTags(anyLong())).thenReturn(tags);

        mockMvc.perform(get("/1/tag"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags[0].tagName").value("tag A"))
                .andExpect(jsonPath("$.tags[1].tagName").value("tag B"));
    }

    @Test
    @DisplayName("Project Tag 생성")
    void post_save_tag_test() throws Exception {
        TagRequest request = new TagRequest();
        request.setTagName("new tag A");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/1/tag")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Tag saved"));
    }

    @Test
    @DisplayName("Project Tag 수정")
    void put_update_tag_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        projectService.saveProject(project);

        Tag tag1 = new Tag("tag A");
        tag1.setProject(project);
        tagService.saveTag(tag1, 1L);

        TagRequest request = new TagRequest();
        request.setTagName("update tag A");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/1/tag/1")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Tag updated"));
    }

    @Test
    @DisplayName("Project Tag 삭제")
    void delete_delete_tag_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        projectService.saveProject(project);

        Tag tag1 = new Tag("tag A");
        tag1.setProject(project);
        tagService.saveTag(tag1, 1L);

        mockMvc.perform(delete("/1/tag/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Tag deleted"));
    }

    @Test
    @DisplayName("Project MileStone 리스트 조회")
    void get_find_all_milestones_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        projectService.saveProject(project);

        MileStone mileStone1 = new MileStone("milestone A", LocalDate.parse("2025-02-01"), LocalDate.parse("2025-02-10"));
        mileStone1.setProject(project);
        MileStone mileStone2 = new MileStone("milestone B");
        mileStone2.setProject(project);

        List<MileStone> mileStones = List.of(mileStone1, mileStone2);
        when(mileStoneService.findMileStones(anyLong())).thenReturn(mileStones);

        mockMvc.perform(get("/1/milestone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.milestones").isArray())
                .andExpect(jsonPath("$.milestones[0].milestoneName").value("milestone A"))
                .andExpect(jsonPath("$.milestones[1].milestoneName").value("milestone B"));
    }

    @Test
    @DisplayName("Poject MileStone 생성")
    void post_save_milestone_test() throws Exception {
        MileStoneRequest request = new MileStoneRequest();
        request.setMilestoneName("new milestone A");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/1/milestone")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Milestone saved"));
    }

    @Test
    @DisplayName("Project MileStone 수정")
    void put_update_milestone_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        projectService.saveProject(project);

        MileStone mileStone1 = new MileStone("milestone A", LocalDate.parse("2025-02-01"), LocalDate.parse("2025-02-10"));
        mileStone1.setProject(project);
        mileStoneService.saveMileStone(mileStone1, 1L);

        MileStoneRequest request = new MileStoneRequest();
        request.setMilestoneName("update milestone A");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/1/milestone/1")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseMessage").value("Milestone updated"));
    }

    @Test
    @DisplayName("Project MileStone 삭제")
    void delete_dete_milestone_test() throws Exception {
        Project project = new Project("Project A", "user1", Status.ACTIVE);
        projectService.saveProject(project);

        MileStone mileStone1 = new MileStone("milestone A", LocalDate.parse("2025-02-01"), LocalDate.parse("2025-02-10"));
        mileStone1.setProject(project);
        mileStoneService.saveMileStone(mileStone1, 1L);

        mockMvc.perform(delete("/1/milestone/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Milestone deleted"));
    }
}
