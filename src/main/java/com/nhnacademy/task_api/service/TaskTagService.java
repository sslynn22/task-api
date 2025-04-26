package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.model.Tag;

import java.util.List;

public interface TaskTagService {
    void saveTaskTag(long taskId, long tagId);
    List<Tag> findTagsByTask(long taskId);
    void deleteTaskTag(long taskId, long tagId);
}
