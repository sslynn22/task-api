package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
