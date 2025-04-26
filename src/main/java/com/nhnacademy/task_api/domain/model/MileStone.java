package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "milestone")
public class MileStone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milestoneId;

    @Setter
    @JoinColumn(name = "project_id")
    @ManyToOne(optional = false)
    private Project project;

    @Setter
    @Length(max = 50)
    private String milestoneName;

    @Setter
    private LocalDate startDate;

    @Setter
    private LocalDate endDate;

    public MileStone(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public MileStone(String milestoneName, LocalDate startDate, LocalDate endDate) {
        this.milestoneName = milestoneName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}