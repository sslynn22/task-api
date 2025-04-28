package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProject_ProjectId(long projectId);
    List<Task> findByMileStone_MilestoneId(long milestoneId);
}