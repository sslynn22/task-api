package com.nhnacademy.task_api.service.Impl;

import com.nhnacademy.task_api.domain.dto.TagRequest;
import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
import com.nhnacademy.task_api.domain.exception.TagNotFoundException;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Tag;
import com.nhnacademy.task_api.repository.ProjectRepository;
import com.nhnacademy.task_api.repository.TagRepository;
import com.nhnacademy.task_api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void saveTag(Tag tag, long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException());
        tag.setProject(project);
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> findTags(long projectId) {
        if(!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException();
        }
        return tagRepository.findAllByProject_ProjectId(projectId);
    }

    @Override
    public void updateTag(long tagId, TagRequest request) {
        if(Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        Optional<Tag> tag = tagRepository.findById(tagId);
        if(tag.isEmpty()) {
            throw new TagNotFoundException();
        }
        request.applyTo(tag.get());
        tagRepository.save(tag.get());
    }

    @Override
    public void deleteTag(long tagId) {
        if(!tagRepository.existsById(tagId)) {
            throw new TagNotFoundException();
        }
        tagRepository.deleteById(tagId);
    }
}
