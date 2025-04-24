package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByProject_ProjectId(long projectId);
}
