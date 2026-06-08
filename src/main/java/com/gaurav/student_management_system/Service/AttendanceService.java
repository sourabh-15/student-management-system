package com.gaurav.student_management_system.Service;

import com.gaurav.student_management_system.Dto.AttendanceRequest;
import com.gaurav.student_management_system.Dto.AttendanceResponse;

import java.util.List;

public interface AttendanceService {
    AttendanceResponse markAttendance(AttendanceRequest request, String username);
    List<AttendanceResponse> getAttendanceByStudentId(Long studentId);
    List<AttendanceResponse> getMyAttendance(String username);
}
