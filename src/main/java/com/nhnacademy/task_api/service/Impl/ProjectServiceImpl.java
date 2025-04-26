package com.nhnacademy.task_api.service.Impl;

import com.nhnacademy.task_api.domain.dto.ProjectRequest;
import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.repository.ProjectRepository;
import com.nhnacademy.task_api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void saveProject(Project project) {
        if(Objects.isNull(project)) {
            throw new IllegalArgumentException();
        }
        projectRepository.save(project);
    }

    @Override
    public List<Project> findProjects(String adminId) {
        if(Objects.isNull(adminId) || adminId.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return projectRepository.findAllByAdminId(adminId);
    }

    @Override
    public Project findProjectById(long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()) {
            throw new ProjectNotFoundException();
        }
        return project.get();
    }

    @Override
    public void updateProject(long projectId, ProjectRequest request) {
        if(Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()) {
            throw new ProjectNotFoundException();
        }
        request.applyTo(project.get());
        projectRepository.save(project.get());
    }

    @Override
    public void deleteProject(long projectId) {
        if(!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException();
        }
        projectRepository.deleteById(projectId);
    }
}
