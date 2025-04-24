package com.nhnacademy.task_api.controller;

import com.nhnacademy.task_api.domain.dto.*;
import com.nhnacademy.task_api.service.ProjectService;
import com.nhnacademy.task_api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProjectController {
    private final ProjectService projectService;
    private final TagService tagService;

    @GetMapping("/")
    public ProjectDTO findAllProjects(@RequestParam String userId) {
        return new ProjectDTO(projectService.findProjects(userId));
    }

    @PostMapping("/")
    public ResponseDTO saveProject(@RequestBody ProjectRequest request) {
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

    @GetMapping("/{project_id}/tag")
    public TagDTO findAllTags(@PathVariable Long project_id) {
        return new TagDTO(tagService.findTags(project_id));
    }

    @PostMapping("/{project_id}/tag")
    public ResponseDTO saveTag(@PathVariable Long project_id,
                          @RequestBody TagRequest request) {
        tagService.saveTag(request.makeTag(), project_id);
        return new ResponseDTO(HttpStatus.OK, "Tag saved");
    }

    @PutMapping("/{project_id}/tag")
    public ResponseDTO updateTag(@PathVariable Long project_id,
                                 @RequestBody TagRequest request) {
        tagService.updateTag(project_id, request);
        return new ResponseDTO(HttpStatus.OK, "Tag updated");
    }

    @DeleteMapping("/{project_id}/tag")
    public ResponseDTO deleteTag(@PathVariable Long project_id) {
        tagService.deleteTag(project_id);
        return new ResponseDTO(HttpStatus.OK, "Tag deleted");
    }
}
