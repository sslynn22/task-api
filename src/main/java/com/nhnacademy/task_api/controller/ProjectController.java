package com.nhnacademy.task_api.controller;

import com.nhnacademy.task_api.domain.dto.*;
import com.nhnacademy.task_api.domain.model.MileStone;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.dto.ProjectDTO;
import com.nhnacademy.task_api.domain.dto.ProjectListDTO;
import com.nhnacademy.task_api.domain.dto.ResponseDTO;
import com.nhnacademy.task_api.domain.dto.TagListDTO;
import com.nhnacademy.task_api.domain.model.Tag;
import com.nhnacademy.task_api.service.MemberService;
import com.nhnacademy.task_api.service.MileStoneService;
import com.nhnacademy.task_api.service.ProjectService;
import com.nhnacademy.task_api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProjectController {
    private final ProjectService projectService;
    private final MemberService memberService;
    private final TagService tagService;
    private final MileStoneService mileStoneService;

    @GetMapping("/")
    public ResponseEntity<ProjectListDTO> findAllProjects(@RequestParam String adminId) {
        List<Project> projects = projectService.findProjects(adminId);
        ProjectListDTO projectListDTO = new ProjectListDTO(projects);

        return ResponseEntity.status(HttpStatus.CREATED).body(projectListDTO);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDTO> saveProject(@RequestBody ProjectRequest request,
                                                   @RequestParam String adminId) {
        request.setAdminId(adminId);    // set : 현재 로그인 된 사용자 id
        projectService.saveProject(request.makeProject());
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Project saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{project_id}")
    public ResponseEntity<ProjectDTO> findProjectById(@PathVariable("project_id") Long project_id) {
        Project project = projectService.findProjectById(project_id);
        ProjectDTO projectDTO = new ProjectDTO(project);

        return ResponseEntity.status(HttpStatus.CREATED).body(projectDTO);
    }

    @PutMapping("/{project_id}")
    public ResponseEntity<ResponseDTO> updateProject(@PathVariable("project_id") Long project_id,
                                                     @RequestBody ProjectRequest request,
                                                     @RequestParam String adminId) {
        request.setAdminId(adminId);
        projectService.updateProject(project_id, request);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Project updated");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{project_id}")
    public ResponseEntity<ResponseDTO> deleteProject(@PathVariable("project_id") Long project_id) {
        projectService.deleteProject(project_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Project deleted");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{project_id}/member")
    public ResponseEntity<MemberIdListDTO> findAllMembers(@PathVariable("project_id") Long project_id) {
        List<String> memberIds = memberService.findMemberIds(project_id);
        MemberIdListDTO memberIdListDTO = new MemberIdListDTO(memberIds);

        return ResponseEntity.status(HttpStatus.CREATED).body(memberIdListDTO);
    }

    @PostMapping("/{project_id}/member/{member_id}")
    public ResponseEntity<ResponseDTO> saveMember(@PathVariable("project_id") Long project_id,
                                                  @PathVariable("member_id") String member_id) {
        memberService.saveMember(member_id, project_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Member saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{project_id}/member/{member_id}")
    public ResponseEntity<ResponseDTO> deleteMember(@PathVariable("project_id") Long project_id,
                                                    @PathVariable("member_id") String member_id) {
        memberService.deleteMember(member_id, project_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Member deleted");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{project_id}/tag")
    public ResponseEntity<TagListDTO> findAllTags(@PathVariable("project_id") Long project_id) {
        List<Tag> tags = tagService.findTags(project_id);
        TagListDTO tagDTO = new TagListDTO(tags);

        return ResponseEntity.status(HttpStatus.CREATED).body(tagDTO);
    }

    @PostMapping("/{project_id}/tag")
    public ResponseEntity<ResponseDTO> saveTag(@PathVariable("project_id") Long project_id,
                                               @RequestBody TagRequest request) {
        tagService.saveTag(request.makeTag(), project_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Tag saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{project_id}/tag")
    public ResponseEntity<ResponseDTO> updateTag(@PathVariable("project_id") Long project_id,
                                                 @RequestBody TagRequest request) {
        tagService.updateTag(project_id, request);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Tag updated");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{project_id}/tag/{tag_id}")
    public ResponseEntity<ResponseDTO> deleteTag(@PathVariable("tag_id") Long tag_id) {
        tagService.deleteTag(tag_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Tag deleted");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{project_id}/milestone")
    public ResponseEntity<MileStoneListDTO> findAllMileStones(@PathVariable("project_id") Long project_id) {
        List<MileStone> milestones = mileStoneService.findMileStones(project_id);
        MileStoneListDTO mileStoneListDTO = new MileStoneListDTO(milestones);

        return ResponseEntity.status(HttpStatus.CREATED).body(mileStoneListDTO);
    }

    @PostMapping("/{project_id}/milestone")
    public ResponseEntity<ResponseDTO> saveMileStone(@PathVariable("project_id") Long project_id,
                                                     @RequestBody MileStoneRequest request) {
        mileStoneService.saveMileStone(request.makeMilestone(), project_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Milestone saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{project_id}/milestone")
    public ResponseEntity<ResponseDTO> updateMileStone(@PathVariable("project_id") Long project_id,
                                                       @RequestBody MileStoneRequest request) {
        mileStoneService.updateMileStone(project_id, request);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Milestone updated");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{project_id}/milestone/{milestone_id}")
    public ResponseEntity<ResponseDTO> deleteMileStone(@PathVariable("milestone_id") Long milestone_id) {
        mileStoneService.deleteMileStone(milestone_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Milestone deleted");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
