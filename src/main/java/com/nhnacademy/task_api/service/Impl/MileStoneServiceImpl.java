package com.nhnacademy.task_api.service.Impl;

import com.nhnacademy.task_api.domain.dto.MileStoneRequest;
import com.nhnacademy.task_api.domain.exception.MileStoneNotFoundException;
import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
import com.nhnacademy.task_api.domain.exception.TaskNotFoundException;
import com.nhnacademy.task_api.domain.model.MileStone;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Task;
import com.nhnacademy.task_api.repository.MileStoneRepository;
import com.nhnacademy.task_api.repository.ProjectRepository;
import com.nhnacademy.task_api.repository.TaskRepository;
import com.nhnacademy.task_api.service.MileStoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MileStoneServiceImpl implements MileStoneService {
    private final MileStoneRepository mileStoneRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Override
    public void saveMileStone(MileStone mileStone, long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException());
        mileStone.setProject(project);
        mileStoneRepository.save(mileStone);
    }

    @Override
    public List<MileStone> findMileStones(long projectId) {
        if(!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException();
        }
        return mileStoneRepository.findAllByProject_ProjectId(projectId);
    }

    @Override
    public MileStone findMileStoneById(long mileStoneId) {
        if(!mileStoneRepository.existsById(mileStoneId)) {
            throw new MileStoneNotFoundException();
        }
        return mileStoneRepository.findById(mileStoneId).get();
    }

    @Override
    public void updateMileStone(long milestoneId, MileStoneRequest request) {
        if(Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        Optional<MileStone> mileStone = mileStoneRepository.findById(milestoneId);
        if(mileStone.isEmpty()) {
            throw new MileStoneNotFoundException();
        }
        request.applyTo(mileStone.get());
        mileStoneRepository.save(mileStone.get());
    }

    @Override
    public void deleteMileStone(long milestoneId) {
        if(!mileStoneRepository.existsById(milestoneId)) {
            throw new MileStoneNotFoundException();
        }
        mileStoneRepository.deleteById(milestoneId);
    }

    @Override
    public void setMileStone(long taskId, long milestoneId) {
        if(!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException();
        }
        if(!mileStoneRepository.existsById(milestoneId)) {
            throw new MileStoneNotFoundException();
        }
        Task task = taskRepository.findById(taskId).get();
        MileStone milestone = mileStoneRepository.findById(milestoneId).get();
        task.setMileStone(milestone);
        taskRepository.save(task);
    }

    @Override
    public void setNullMileStone(long taskId) {
        if(!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException();
        }
        Task task = taskRepository.findById(taskId).get();
        task.setMileStone(null);
        taskRepository.save(task);
    }
}
