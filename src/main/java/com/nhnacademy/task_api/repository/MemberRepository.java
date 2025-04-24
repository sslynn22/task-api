package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
