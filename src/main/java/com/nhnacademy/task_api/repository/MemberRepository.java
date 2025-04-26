package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Member;
import com.nhnacademy.task_api.domain.model.MemberPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, MemberPk> {
    List<Member> findAllByProject_ProjectId(long projectId);
}
