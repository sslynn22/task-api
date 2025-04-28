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
@RequestMapping("/miniDooray")
public class ProjectController {
    private final ProjectService projectService;
    private final MemberService memberService;
    private final TagService tagService;
    private final MileStoneService mileStoneService;

    // 로그인 한 유저의 Project 목록 조회
    @GetMapping("/")
    public ResponseEntity<ProjectListDTO> findAllProjects(@RequestParam String adminId) {
        List<Project> projects = projectService.findProjects(adminId);
        ProjectListDTO projectListDTO = new ProjectListDTO(projects);

        return ResponseEntity.status(HttpStatus.OK).body(projectListDTO);
    }

    // 새로운 Project 생성
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> saveProject(@RequestBody ProjectRequest request) {
        projectService.saveProject(request.makeProject());
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Project saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Project 단일 조회 (상세 조회)
    @GetMapping("/{project_id}")
    public ResponseEntity<ProjectDTO> findProjectById(@PathVariable("project_id") Long project_id) {
        Project project = projectService.findProjectById(project_id);
        ProjectDTO projectDTO = new ProjectDTO(
                project.getProjectId(),
                project.getProjectName(),
                project.getCreatedAt(),
                project.getAdminId(),
                project.getProjectStatus());

        return ResponseEntity.status(HttpStatus.OK).body(projectDTO);
    }

    // Project 수정
    @PutMapping("/{project_id}")
    public ResponseEntity<ResponseDTO> updateProject(@PathVariable("project_id") Long project_id,
                                                     @RequestBody ProjectRequest request,
                                                     @RequestParam String adminId) {
        request.setAdminId(adminId);
        projectService.updateProject(project_id, request);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Project updated");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Project 삭제
    @DeleteMapping("/{project_id}")
    public ResponseEntity<ResponseDTO> deleteProject(@PathVariable("project_id") Long project_id) {
        projectService.deleteProject(project_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "Project deleted");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Project에 속한 멤버 리스트 조회
    @GetMapping("/{project_id}/member")
    public ResponseEntity<MemberIdListDTO> findAllMembers(@PathVariable("project_id") Long project_id) {
        List<String> memberIds = memberService.findMemberIds(project_id);
        MemberIdListDTO memberIdListDTO = new MemberIdListDTO(memberIds);

        return ResponseEntity.status(HttpStatus.OK).body(memberIdListDTO);
    }

    // Project에 새로운 멤버 추가
    @PostMapping("/{project_id}/member/{member_id}")
    public ResponseEntity<ResponseDTO> saveMember(@PathVariable("project_id") Long project_id,
                                                  @PathVariable("member_id") String member_id) {
        memberService.saveMember(member_id, project_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Member saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Project에 속한 멤버 삭제
    @DeleteMapping("/{project_id}/member/{member_id}")
    public ResponseEntity<ResponseDTO> deleteMember(@PathVariable("project_id") Long project_id,
                                                    @PathVariable("member_id") String member_id) {
        memberService.deleteMember(member_id, project_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "Member deleted");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Project Tag 리스트 조회 (Tag 설정 창으로 이동할 때 목록 보여주는 용도)
    @GetMapping("/{project_id}/tag")
    public ResponseEntity<TagListDTO> findAllTags(@PathVariable("project_id") Long project_id) {
        List<Tag> tags = tagService.findTags(project_id);
        TagListDTO tagDTO = new TagListDTO(tags);

        return ResponseEntity.status(HttpStatus.OK).body(tagDTO);
    }

    // Project Tag 생성
    @PostMapping("/{project_id}/tag")
    public ResponseEntity<ResponseDTO> saveTag(@PathVariable("project_id") Long project_id,
                                               @RequestBody TagRequest request) {
        tagService.saveTag(request.makeTag(), project_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Tag saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Project Tag 수정
    @PutMapping("/{project_id}/tag/{tag_id}")
    public ResponseEntity<ResponseDTO> updateTag(@PathVariable("tag_id") Long tag_id,
                                                 @RequestBody TagRequest request) {
        tagService.updateTag(tag_id, request);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Tag updated");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Project Tag 삭제
    @DeleteMapping("/{project_id}/tag/{tag_id}")
    public ResponseEntity<ResponseDTO> deleteTag(@PathVariable("tag_id") Long tag_id) {
        tagService.deleteTag(tag_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "Tag deleted");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Project MileStone 리스트 조회 (MileStone 설정 창으로 이동할 때 목록 보여주는 용도)
    @GetMapping("/{project_id}/milestone")
    public ResponseEntity<MileStoneListDTO> findAllMileStones(@PathVariable("project_id") Long project_id) {
        List<MileStone> milestones = mileStoneService.findMileStones(project_id);
        MileStoneListDTO mileStoneListDTO = new MileStoneListDTO(milestones);

        return ResponseEntity.status(HttpStatus.OK).body(mileStoneListDTO);
    }

    // Project MileStone 생성
    @PostMapping("/{project_id}/milestone")
    public ResponseEntity<ResponseDTO> saveMileStone(@PathVariable("project_id") Long project_id,
                                                     @RequestBody MileStoneRequest request) {
        mileStoneService.saveMileStone(request.makeMilestone(), project_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Milestone saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Project MileStone 수정
    @PutMapping("/{project_id}/milestone/{milestone_id}")
    public ResponseEntity<ResponseDTO> updateMileStone(@PathVariable("milestone_id") Long milestone_id,
                                                       @RequestBody MileStoneRequest request) {
        mileStoneService.updateMileStone(milestone_id, request);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "Milestone updated");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Project MileStone 삭제
    @DeleteMapping("/{project_id}/milestone/{milestone_id}")
    public ResponseEntity<ResponseDTO> deleteMileStone(@PathVariable("milestone_id") Long milestone_id) {
        mileStoneService.deleteMileStone(milestone_id);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "Milestone deleted");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
