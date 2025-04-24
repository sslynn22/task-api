package com.nhnacademy.task_api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.*;

import java.io.Serializable;

// 이거 주석 삭제하고 사용
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class MileStonePk implements Serializable {
    private long mileStoneId;
    private long projectId;
}
