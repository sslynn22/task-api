package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
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
        when(mileStoneRepository.findById(anyLong())).thenReturn(Optional.of(mileStone));

    }

    @Test
    @DisplayName("find milestone - fail")
    void find_milestone_fail_test() {

    }
}
