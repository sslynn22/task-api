package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Project;

import java.util.List;

public interface ProjectQuerydslRepository {
    List<Project> findAllByProject_memberId(String memberId);
}
