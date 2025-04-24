package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.Tag;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TagDTO {
    List<Tag> tags;
}
