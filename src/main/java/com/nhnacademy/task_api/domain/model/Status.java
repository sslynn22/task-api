package com.nhnacademy.task_api.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Status {
    ACTIVE, DORMANT, COMPLETED;

    @JsonCreator
    public static Status fromString(String str){
        for (Status value : Status.values()) {
            if (value.name().equalsIgnoreCase(str)) {
                return value;
            }
        }
        return ACTIVE;
    }
}
