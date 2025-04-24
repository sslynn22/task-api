package com.nhnacademy.task_api.controller;

import com.nhnacademy.task_api.domain.dto.ProjectDTO;
import com.nhnacademy.task_api.domain.dto.ProjectRequest;
import com.nhnacademy.task_api.domain.dto.ResponseDTO;
import com.nhnacademy.task_api.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/")
    public ProjectDTO findAllProjects(@RequestParam String adminId) {
        return new ProjectDTO(projectService.findProjects(adminId));
    }

    @PostMapping("/")
    public ResponseDTO saveProject(@RequestBody ProjectRequest request, HttpServletRequest req) {
        projectService.saveProject(request.makeProject());
        return new ResponseDTO(HttpStatus.OK, "Project saved");
    }

    @GetMapping("/{project_id}")
    public ProjectDTO findProjectById(@PathVariable Long project_id) {
        return new ProjectDTO(projectService.findProjectById(project_id));
    }

    @PutMapping("/{project_id}")
    public ResponseDTO updateProject(@PathVariable Long project_id,
                                     @RequestBody ProjectRequest request) {
        projectService.updateProject(project_id, request);
        return new ResponseDTO(HttpStatus.OK, "Project updated");
    }

    @DeleteMapping("/{project_id}")
    public ResponseDTO deleteProject(@PathVariable Long project_id) {
        projectService.deleteProject(project_id);
        return new ResponseDTO(HttpStatus.OK, "Project deleted");
    }
}
