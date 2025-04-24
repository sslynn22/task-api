package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class TaskTagPk implements Serializable {
    private long tagId;
    private long taskId;
}
