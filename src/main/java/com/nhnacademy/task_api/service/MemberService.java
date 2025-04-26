package com.nhnacademy.task_api.service;

import java.util.List;

public interface MemberService {
    void saveMember(String memberId, long projectId);
    List<String> findMemberIds(long projectId);
    void deleteMember(String memberId, long projectId);
}
