package com.nhnacademy.task_api.service.Impl;

import com.nhnacademy.task_api.domain.exception.MemberNotFoundException;
import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
import com.nhnacademy.task_api.domain.model.Member;
import com.nhnacademy.task_api.domain.model.MemberPk;
import com.nhnacademy.task_api.repository.MemberRepository;
import com.nhnacademy.task_api.repository.ProjectRepository;
import com.nhnacademy.task_api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void saveMember(String memberId, long projectId) {
        if(!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException();
        }
        memberRepository.save(new Member(memberId, projectRepository.findById(projectId).get()));
    }

    @Override
    public List<String> findMemberIds(long projectId) {
        if(!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException();
        }
        List<Member> members = memberRepository.findAllByProject_ProjectId(projectId);
        List<String> memberIds = members.stream()
                .map(Member::getMemberPK)
                .map(MemberPk::getMemberId)
                .toList();
        return memberIds;
    }

    @Override
    public void deleteMember(String memberId, long projectId) {
        MemberPk pk = new MemberPk(memberId, projectId);

        if(!memberRepository.existsById(pk)) {
            throw new MemberNotFoundException();
        }
        memberRepository.deleteById(pk);
    }
}
