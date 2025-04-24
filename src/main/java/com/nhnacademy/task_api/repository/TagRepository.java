package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
