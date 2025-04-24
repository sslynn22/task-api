package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {
}
