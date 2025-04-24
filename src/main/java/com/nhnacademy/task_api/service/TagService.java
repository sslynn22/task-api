package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.dto.TagRequest;
import com.nhnacademy.task_api.domain.model.Tag;

import java.util.List;

public interface TagService {
    void saveTag(Tag tag, long projectId);
    List<Tag> findTags(long projectId);
    void updateTag(long tagId, TagRequest request);
    void deleteTag(long tagId);
}
