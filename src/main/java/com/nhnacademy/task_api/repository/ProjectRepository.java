package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectQuerydslRepository {
    List<Project> findAllByAdminId(String adminId);
}
