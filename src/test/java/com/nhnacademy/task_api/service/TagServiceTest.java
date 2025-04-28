package com.nhnacademy.task_api.service;

import com.nhnacademy.task_api.domain.dto.TagRequest;
import com.nhnacademy.task_api.domain.exception.ProjectNotFoundException;
import com.nhnacademy.task_api.domain.exception.TagNotFoundException;
import com.nhnacademy.task_api.domain.model.Project;
import com.nhnacademy.task_api.domain.model.Status;
import com.nhnacademy.task_api.domain.model.Tag;
import com.nhnacademy.task_api.repository.ProjectRepository;
import com.nhnacademy.task_api.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TagServiceTest {
    @Autowired
    private TagService tagService;
    @MockitoBean
    private TagRepository tagRepository;
    @MockitoBean
    private ProjectRepository projectRepository;

    private Project project;
    private Tag tag;

    @BeforeEach
    void setUp() {
        project = new Project("Project A", "user1", Status.ACTIVE);
        projectRepository.save(project);
        tag = new Tag("tag name");
        tag.setProject(project);
    }

    @Test
    @DisplayName("save tag")
    void save_tag_test() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);
        tagService.saveTag(tag, 1L);

        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    @DisplayName("find tags - success")
    void find_tags_success_test() {
        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.findAllByProject_ProjectId(anyLong())).thenReturn(List.of(tag));
        List<Tag> tags = tagService.findTags(1L);

        assertThat(tags).hasSize(1);
        assertThat(tags.getFirst()).isEqualTo(tag);
    }

    @Test
    @DisplayName("find tags - fail")
    void find_tags_fail_test() {
        when(projectRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> tagService.findTags(10L))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    @DisplayName("update tag - success")
    void update_tag_success_test() {
        TagRequest request = new TagRequest();
        request.setTagName("new tag name");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        tagService.updateTag(1L, request);

        assertThat(tag.getTagName()).isEqualTo("new tag name");
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    @DisplayName("update tag - fail1")
    void update_tag_fail1_test() {
        assertThatThrownBy(() -> tagService.updateTag(1L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("update tag - fail2")
    void update_tag_fail2_test() {
        TagRequest request = new TagRequest();
        request.setTagName("new tag name");

        when(tagRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.updateTag(10L, request))
                .isInstanceOf(TagNotFoundException.class);
    }

    @Test
    @DisplayName("delete tag - success")
    void delete_tag_success_test() {
        when(tagRepository.existsById(1L)).thenReturn(true);
        tagService.deleteTag(1L);

        verify(tagRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete tag - fail")
    void delete_tag_fail_test() {
        when(tagRepository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> tagService.deleteTag(10L))
                .isInstanceOf(TagNotFoundException.class);
    }
}
