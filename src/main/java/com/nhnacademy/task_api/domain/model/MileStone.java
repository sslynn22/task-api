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

// 이거 주석 삭제하고 사용
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "milestone")
public class MileStone {
    @EmbeddedId
    MileStonePk mileStonePk;

    @JoinColumn(name = "project_id")
    @MapsId("projectId")
    @ManyToOne(optional = false)
    private Project project;

    @Setter
    @NotNull
    @Length(max = 50)
    private String mileStoneName;

    @Setter
    private ZonedDateTime startDate;

    @Setter
    private ZonedDateTime endDate;

    public MileStone(MileStonePk mileStonePk, Project project, String mileStoneName, ZonedDateTime startDate, ZonedDateTime endDate) {
        this.mileStonePk = mileStonePk;
        this.project = project;
        this.mileStoneName = mileStoneName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
