package com.davacom.employeemanagemnetsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HttpResponse {
    @JsonFormat(pattern="dd-MM-yyy HH:mm:ss", timezone = "Africa/Lagos")
    private Date timeStamp;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;
}
