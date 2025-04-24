package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
