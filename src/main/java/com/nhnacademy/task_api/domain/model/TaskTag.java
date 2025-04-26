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

    @JoinColumn(name = "task_id")
    @MapsId("taskId")   // 식별관계일 때 사용
    @ManyToOne(optional = false)
    private Task task;

    @JoinColumn(name = "tag_id")
    @MapsId("tagId")
    @ManyToOne(optional = false)
    private Tag tag;

    public TaskTag(Task task, Tag tag) {
        this.taskTagPk = new TaskTagPk(task.getTaskId(), tag.getTagId());
        this.task = task;
        this.tag = tag;
    }
}
