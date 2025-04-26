package com.nhnacademy.task_api.domain.dto;

import com.nhnacademy.task_api.domain.model.MileStone;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MileStoneRequest {
    private String milestoneName;
    private LocalDate startDate;
    private LocalDate endDate;

    public MileStone makeMilestone() {
        if(startDate == null || endDate == null) {
            return new MileStone(milestoneName);
        } else {
            return new MileStone(milestoneName, startDate, endDate);
        }
    }

    public void applyTo(MileStone mileStone) {
        if(startDate == null || endDate == null) {
            mileStone.setMilestoneName(milestoneName);
        } else {
            mileStone.setMilestoneName(milestoneName);
            mileStone.setStartDate(startDate);
            mileStone.setEndDate(endDate);
        }
    }
}
