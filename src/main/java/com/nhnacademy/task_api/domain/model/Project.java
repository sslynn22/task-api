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
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectId;

    @Setter
    @NotNull
    @Length(min = 3, max = 30)
    private String projectName;

    @Setter
    @NotNull
    private LocalDateTime createdAt;    // now()로 들어가도록

    @Setter
    @NotNull
    @Length(max = 50)
    private String userId;

    @Setter
    @NotNull
    private Status projectStatus;

    public Project(long projectId, String projectName, String userId, Status projectStatus) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.userId = userId;
        this.projectStatus = projectStatus;
    }
}
