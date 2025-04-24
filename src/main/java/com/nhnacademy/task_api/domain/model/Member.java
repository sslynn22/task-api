package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member {
    @EmbeddedId
    @Valid
    private MemberPK memberPK;

    @JoinColumn(name = "project_id")
    @MapsId("projectId")
    @ManyToOne(optional = false)
    private Project project;

    public Member(MemberPK memberPK, Project project) {
        this.memberPK = memberPK;
        this.project = project;
    }
}
