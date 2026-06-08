package com.gaurav.student_management_system.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceRequest {

    @NotNull
    private LocalDate attendanceDate;

    @NotNull
    private Boolean present;

    @NotNull
    private Long studentId;
}
