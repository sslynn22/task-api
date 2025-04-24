package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskId;

    @Setter
    @NotNull
    @Length(max = 50)
    private String taskName;

    @Setter
    @NotNull
    @Length(max = 50)
    private String userId;

    @Setter
    @Length(max = 50)
    private String managerId;

    @Setter
    @NotNull
    private LocalDateTime createdAt;    // now()로 들어가도록

    @JoinColumn(name = "project_id")
    @ManyToOne(optional = false)
    private Project project;

    // milestone 추가 필요

    public Task(long taskId, String taskName, String userId, String managerId, Project project) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.userId = userId;
        this.managerId = managerId;
        this.project = project;
    }
}
