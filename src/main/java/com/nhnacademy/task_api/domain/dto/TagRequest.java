package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.Tag;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagRequest {
    private String tagName;

    public Tag makeTag() {
        return new Tag(tagName);
    }

    public void applyTo(Tag tag) {
        tag.setTagName(tagName);
    }
}
