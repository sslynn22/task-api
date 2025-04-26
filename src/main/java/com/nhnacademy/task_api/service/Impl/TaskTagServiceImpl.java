package com.nhnacademy.task_api.service.Impl;

import com.nhnacademy.task_api.domain.exception.TagNotFoundException;
import com.nhnacademy.task_api.domain.exception.TaskNotFoundException;
import com.nhnacademy.task_api.domain.exception.TaskTagNotFoundException;
import com.nhnacademy.task_api.domain.model.Tag;
import com.nhnacademy.task_api.domain.model.TaskTag;
import com.nhnacademy.task_api.domain.model.TaskTagPk;
import com.nhnacademy.task_api.repository.TagRepository;
import com.nhnacademy.task_api.repository.TaskRepository;
import com.nhnacademy.task_api.repository.TaskTagRepository;
import com.nhnacademy.task_api.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskTagServiceImpl implements TaskTagService {
    private final TaskTagRepository taskTagRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    @Override
    public void saveTaskTag(long taskId, long tagId) {
        if(!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException();
        }
        if(!tagRepository.existsById(tagId)) {
            throw new TagNotFoundException();
        }
        taskTagRepository.save(new TaskTag(taskRepository.findById(taskId).get(), tagRepository.findById(tagId).get()));
    }

    @Override
    public List<Tag> findTagsByTask(long taskId) {
        if(!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException();
        }
        List<TaskTag> taskTags = taskTagRepository.findAllByTask_TaskId(taskId);
        List<Tag> tags = taskTags.stream()
                .map(TaskTag::getTag)
                .toList();
        return tags;
    }

    @Override
    public void deleteTaskTag(long taskId, long tagId) {
        TaskTagPk pk = new TaskTagPk(taskId, tagId);

        if(!taskTagRepository.existsById(pk)) {
            throw new TaskTagNotFoundException();
        }
        taskTagRepository.deleteById(pk);
    }
}
