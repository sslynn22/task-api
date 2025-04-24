package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "milestone")
public class MileStone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milestoneId;

    @JoinColumn(name = "project_id")
    @ManyToOne(optional = false)
    private Project project;

    @Setter
    @Length(max = 50)
    private String mileStoneName;

    @Setter
    private ZonedDateTime startDate;

    @Setter
    private ZonedDateTime endDate;

    public MileStone(Long milestoneId, Project project, String mileStoneName, ZonedDateTime startDate, ZonedDateTime endDate) {
        this.milestoneId = milestoneId;
        this.project = project;
        this.mileStoneName = mileStoneName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}