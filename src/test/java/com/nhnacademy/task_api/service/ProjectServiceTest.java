package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.dto.ProjectRequest;
import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Status;
import com.nhnacademy.task_api.repository.ProjectRepository;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;
    @MockitoBean
    private ProjectRepository projectRepository;

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project("Project A", "user1", Status.ACTIVE);
    }

    @Test
    @DisplayName("save project - success")
    void save_project_success_test() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        projectService.saveProject(project);

        verify(projectRepository, times(1)).save(project);
    }

    @Test
    @DisplayName("save project - fail")
    void save_project_fail_test() {
        assertThatThrownBy(() -> projectService.saveProject(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("find projects - success")
    void find_projects_success_test() {
        when(projectRepository.findAllByAdminId("user1")).thenReturn(List.of(project));
        List<Project> projects = projectService.findProjects("user1");

        assertThat(projects).hasSize(1);
        assertThat(projects.getFirst()).isEqualTo(project);
    }

    @Test
    @DisplayName("find projects - fail1")
    void find_projects_fail1_test() {
        assertThatThrownBy(() -> projectService.findProjects(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("find projects - fail2")
    void find_projects_fail2_test() {
        assertThatThrownBy(() -> projectService.findProjects(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("find project by id - success")
    void find_project_by_id_success_test() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Project findProject = projectService.findProjectById(1L);

        assertThat(findProject).isNotNull();
        assertThat(findProject).isEqualTo(project);
    }

    @Test
    @DisplayName("find project by id - fail")
    void find_project_by_id_fail_test() {
        when(projectRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.findProjectById(99L))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    @DisplayName("update project - success")
    void update_project_success_test() {
        ProjectRequest request = new ProjectRequest();
        request.setProjectName("new project name");
        request.setAdminId("user1");
        request.setProjectStatus(Status.COMPLETED);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        projectService.updateProject(1L, request);

        assertThat(project.getProjectName()).isEqualTo("new project name");
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    @DisplayName("update project - fail1")
    void update_project_fail1_test() {
        assertThatThrownBy(() -> projectService.updateProject(1L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("update project - fail2")
    void update_project_fail2_test() {
        ProjectRequest request = new ProjectRequest();
        request.setProjectName("new project name");
        request.setAdminId("user1");
        request.setProjectStatus(Status.COMPLETED);

        when(projectRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.updateProject(10L, request))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    @DisplayName("delete project - success")
    void delete_project_success_test() {
        when(projectRepository.existsById(1L)).thenReturn(true);
        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete project - fail")
    void delete_project_fail_test() {
        when(projectRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> projectService.deleteProject(10L))
                .isInstanceOf(ProjectNotFoundException.class);
    }
}
