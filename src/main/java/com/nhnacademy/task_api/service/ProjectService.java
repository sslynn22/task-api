package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.model.Project;

import java.util.List;

public interface ProjectService {
    void saveProject(Project project);
    List<Project> findProjects(String userId);
    Project findProjectById(Long projectId);
    void updateProject(Project project);
    void deleteProject(Long projectId);
}
