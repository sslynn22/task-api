package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {
    private String projectName;
    private String adminId;
    private Status projectStatus;


    public Project makeProject() {
        return new Project(projectName, adminId, projectStatus);
    }

    public void applyTo(Project project) {
        project.setProjectName(projectName);
        project.setAdminId(adminId);
        project.setProjectStatus(projectStatus);
    }

}
