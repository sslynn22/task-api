package com.nhnacademy.task_api.repository;

import com.nhnacademy.task_api.domain.model.MileStone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileStoneRepository extends JpaRepository<MileStone, Long> {
}
