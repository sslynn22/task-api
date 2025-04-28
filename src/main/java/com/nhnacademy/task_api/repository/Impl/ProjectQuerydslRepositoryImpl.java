package com.nhnacademy.task_api.repository.Impl;

import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.repository.ProjectQuerydslRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nhnacademy.task_api.domain.model.QProject.project;
import static com.nhnacademy.task_api.domain.model.QMember.member;

@Transactional(readOnly = true)
public class ProjectQuerydslRepositoryImpl extends QuerydslRepositorySupport implements ProjectQuerydslRepository {
    public ProjectQuerydslRepositoryImpl() {
        super(Project.class);
    }

    @Override
    public List<Project> findAllByProject_memberId(String memberId) {
        return from(member)
                .leftJoin(member.project,project)
                .where(member.memberPK.memberId.eq(memberId))
                .select(project)
                .fetch();
    }
}
