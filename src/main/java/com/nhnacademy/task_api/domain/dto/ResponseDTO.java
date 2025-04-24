package com.nhnacademy.task_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class ResponseDTO {
    HttpStatus httpStatus;
    String responseMessage;
}
