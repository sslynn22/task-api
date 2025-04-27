package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.dto.MileStoneRequest;
import com.nhnacademy.task_api.domain.model.MileStone;

import java.util.List;

public interface MileStoneService {
    void saveMileStone(MileStone mileStone, long projectId);
    List<MileStone> findMileStones(long projectId);
    MileStone findMileStoneById(long mileStoneId);
    void updateMileStone(long milestoneId, MileStoneRequest request);
    void deleteMileStone(long milestoneId);
    void setMileStone(long taskId, long milestoneId);
    void setNullMileStone(long taskId);
}
