package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class MemberPk implements Serializable {
    @Column(length = 50)
    private String memberId;
    private long projectId;
}
