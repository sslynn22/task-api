package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.dto.ProjectRequest;
import com.nhnacademy.task_api.domain.model.Project;

import java.util.List;

public interface ProjectService {
    void saveProject(Project project);
    List<Project> findProjects(String adminId);
    Project findProjectById(long projectId);
    void updateProject(long projectId, ProjectRequest request);
    void deleteProject(long projectId);
}
