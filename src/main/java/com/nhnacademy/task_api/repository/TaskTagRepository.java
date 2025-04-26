package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.TaskTag;
import com.nhnacademy.task_api.domain.model.TaskTagPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTagPk> {
    List<TaskTag> findAllByTask_TaskId(Long taskId);
}
