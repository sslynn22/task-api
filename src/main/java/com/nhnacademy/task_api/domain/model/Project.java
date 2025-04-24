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
    private LocalDateTime createdAt;

    @Setter
    @NotNull
    @Length(max = 50)
    private String adminId;

    @Setter
    @NotNull
    private Status projectStatus;

    public Project(String projectName, String adminId, Status projectStatus) {
        this.projectName = projectName;
        this.adminId = adminId;
        this.projectStatus = projectStatus;
    }
}
