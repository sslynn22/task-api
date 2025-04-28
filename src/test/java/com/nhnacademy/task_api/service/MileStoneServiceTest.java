package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.dto.MileStoneRequest;
import com.nhnacademy.task_api.domain.exception.MileStoneNotFoundException;
import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
import com.nhnacademy.task_api.domain.exception.TaskNotFoundException;
import com.nhnacademy.task_api.domain.model.MileStone;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Status;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.repository.MileStoneRepository;
import com.nhnacademy.task_api.repository.ProjectRepository;
import com.nhnacademy.task_api.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MileStoneServiceTest {
    @Autowired
    private MileStoneService mileStoneService;
    @MockitoBean
    private MileStoneRepository mileStoneRepository;
    @MockitoBean
    private ProjectRepository projectRepository;
    @MockitoBean
    private TaskRepository taskRepository;

    private Project project;
    private Task task;
    private MileStone mileStone;

    @BeforeEach
    void setUp() {
        project = new Project("Project A", "user1", Status.ACTIVE);
        projectRepository.save(project);
        task = new Task("Task A", "user1", "user2", project);
        taskRepository.save(task);
        mileStone = new MileStone("milestone name");
    }

    @Test
    @DisplayName("save milestone")
    void save_milestone_test() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(mileStoneRepository.save(any(MileStone.class))).thenReturn(mileStone);
        mileStoneService.saveMileStone(mileStone, 1L);

        verify(mileStoneRepository, times(1)).save(mileStone);
    }

    @Test
    @DisplayName("find milestones by id - success")
    void find_milestones_success_test() {
        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(mileStoneRepository.findAllByProject_ProjectId(anyLong())).thenReturn(List.of(mileStone));
        List<MileStone> mileStones = mileStoneService.findMileStones(1L);

        assertThat(mileStones).hasSize(1);
        assertThat(mileStones.getFirst()).isEqualTo(mileStone);
    }

    @Test
    @DisplayName("find milestones by id - fail")
    void find_milestones_fail_test() {
        when(projectRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> mileStoneService.findMileStones(10L))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    @DisplayName("find milestone - success")
    void find_milestone_success_test() {
        when(mileStoneRepository.existsById(anyLong())).thenReturn(true);
        when(mileStoneRepository.findById(anyLong())).thenReturn(Optional.of(mileStone));
        MileStone findMileStone = mileStoneService.findMileStoneById(1L);

        assertThat(findMileStone).isNotNull();
        assertThat(findMileStone).isEqualTo(mileStone);
    }

    @Test
    @DisplayName("find milestone - fail")
    void find_milestone_fail_test() {
        when(mileStoneRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> mileStoneService.findMileStoneById(10L))
                .isInstanceOf(MileStoneNotFoundException.class);
    }

    @Test
    @DisplayName("update milestone - success")
    void update_milestone_success_test() {
        MileStoneRequest request = new MileStoneRequest();
        request.setMilestoneName("new milestone name");

        when(mileStoneRepository.findById(1L)).thenReturn(Optional.of(mileStone));
        mileStoneService.updateMileStone(1L, request);

        assertThat(mileStone.getMilestoneName()).isEqualTo("new milestone name");
        verify(mileStoneRepository, times(1)).save(any(MileStone.class));
    }

    @Test
    @DisplayName("update milestone - fail1")
    void update_milestone_fail1_test() {
        assertThatThrownBy(() -> mileStoneService.updateMileStone(1L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("update milestone - fail2")
    void update_milestone_fail2_test() {
        MileStoneRequest request = new MileStoneRequest();
        request.setMilestoneName("new milestone name");

        when(mileStoneRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mileStoneService.updateMileStone(10L, request))
                .isInstanceOf(MileStoneNotFoundException.class);
    }

    @Test
    @DisplayName("delete milestone - success")
    void delete_milestone_success_test() {
        when(mileStoneRepository.existsById(1L)).thenReturn(true);
        mileStoneService.deleteMileStone(1L);

        verify(mileStoneRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete milestone - fail")
    void delete_milestone_fail_test() {
        when(mileStoneRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> mileStoneService.deleteMileStone(10L))
                .isInstanceOf(MileStoneNotFoundException.class);
    }

    @Test
    @DisplayName("task set milestone - success")
    void task_set_milestone_success_test() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(mileStoneRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(mileStoneRepository.findById(1L)).thenReturn(Optional.of(mileStone));
        mileStoneService.setMileStone(1L, 1L);

        verify(taskRepository, atLeast(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("task set milestone - fail1")
    void task_set_milestone_fail1_test() {
        when(taskRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> mileStoneService.setMileStone(10L, 1L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    @DisplayName("task set milestone - fail2")
    void task_set_milestone_fail2_test() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(mileStoneRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> mileStoneService.setMileStone(1L, 10L))
                .isInstanceOf(MileStoneNotFoundException.class);
    }

    @Test
    @DisplayName("task set null milestone - success")
    void task_set_null_milestone_success_test() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        mileStoneService.setNullMileStone(1L);

        verify(taskRepository, atLeast(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("task set null milestone - fail")
    void task_set_null_milestone_fail_test() {
        when(taskRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> mileStoneService.setNullMileStone(10L))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
