package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.exception.MemberNotFoundException;
import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
import com.nhnacademy.task_api.domain.model.Member;
import com.nhnacademy.task_api.domain.model.MemberPk;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Status;
import com.nhnacademy.task_api.repository.MemberRepository;
import com.nhnacademy.task_api.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @MockitoBean
    private MemberRepository memberRepository;
    @MockitoBean
    private ProjectRepository projectRepository;

    private Project project;
    private Member member;
    private String memberId;

    @BeforeEach
    void setUp() {
        project = new Project("Project A", "user1", Status.ACTIVE);
        projectRepository.save(project);
        memberId = "user2";
        member = new Member(memberId, project);
    }

    @Test
    @DisplayName("save member - success")
    void save_member_success_test() {
        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        memberService.saveMember(memberId, 1L);

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("save member - fail")
    void save_member_fail_test() {
        when(projectRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> memberService.saveMember(memberId, 10L))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    @DisplayName("find member ids - success")
    void find_member_ids_success_test() {
        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(memberRepository.findAllByProject_ProjectId(anyLong())).thenReturn(List.of(member));
        List<String> members = memberService.findMemberIds(1L);

        assertThat(members).hasSize(1);
        assertThat(members.getFirst()).isEqualTo(memberId);
    }

    @Test
    @DisplayName("find member ids - fail")
    void find_member_ids_fail_test() {
        when(projectRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> memberService.findMemberIds(10L))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    @DisplayName("delete member - success")
    void delete_member_success_test() {
        when(memberRepository.existsById(any(MemberPk.class))).thenReturn(true);
        memberService.deleteMember(memberId, 1L);

        verify(memberRepository, times(1)).deleteById(any(MemberPk.class));
    }

    @Test
    @DisplayName("delete member - fail")
    void delete_member_fail_test() {
        when(memberRepository.existsById(any(MemberPk.class))).thenReturn(false);

        assertThatThrownBy(() -> memberService.deleteMember(memberId, 1L))
                .isInstanceOf(MemberNotFoundException.class);
    }
}
