package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TaskTag {
    @EmbeddedId
    private TaskTagPk taskTagPk;

    @JoinColumn(name = "task_id") // 식별관계일 때 사용
    @MapsId("taskId")
    @ManyToOne(optional = false)
    private Task task;

    @JoinColumn(name = "tag_id")
    @MapsId("tagId")
    @ManyToOne(optional = false)
    private Tag tag;
}
