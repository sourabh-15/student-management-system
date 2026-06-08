package com.gaurav.student_management_system.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AttendanceResponse {
    private Long id;
    private LocalDate attendanceDate;
    private Boolean present;
    private Long studentId;
    private String studentName;
    private String markedBy;
}
