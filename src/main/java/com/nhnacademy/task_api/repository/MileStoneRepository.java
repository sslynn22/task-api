package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.MileStone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MileStoneRepository extends JpaRepository<MileStone, Long> {
    List<MileStone> findAllByProject_ProjectId(long projectId);
}
