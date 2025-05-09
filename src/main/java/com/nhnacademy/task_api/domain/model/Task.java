package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "task")
public class Task {
    @Setter
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
    private LocalDateTime createdAt;

    @Setter
    @JoinColumn(name = "project_id")
    @ManyToOne(optional = false)
    private Project project;

    @Setter
    @JoinColumn(name = "milestone_id")
    @ManyToOne
    private MileStone mileStone;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TaskTag> taskTags = new ArrayList<>();

    public Task(String taskName, String userId, String managerId, Project project) {
        this.taskName = taskName;
        this.userId = userId;
        this.managerId = managerId;
        this.createdAt = LocalDateTime.now();
        this.project = project;
        this.mileStone = null;
    }

    public Task(String taskName, String userId, String managerId) {
        this.taskName = taskName;
        this.userId = userId;
        this.managerId = managerId;
        this.createdAt = LocalDateTime.now();
        this.mileStone = null;
    }
}