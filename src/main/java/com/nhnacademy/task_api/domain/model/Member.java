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
    private MemberPk memberPK;

    @JoinColumn(name = "project_id")
    @MapsId("projectId")
    @ManyToOne(optional = false)
    private Project project;

    public Member(String memberId, Project project) {
        this.memberPK = new MemberPk(memberId, project.getProjectId());
        this.project = project;
    }
}
