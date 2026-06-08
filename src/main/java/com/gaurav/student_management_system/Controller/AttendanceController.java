package com.gaurav.student_management_system.Controller;

import com.gaurav.student_management_system.Dto.AttendanceRequest;
import com.gaurav.student_management_system.Dto.AttendanceResponse;
import com.gaurav.student_management_system.Service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public AttendanceResponse markAttendance(@Valid @RequestBody AttendanceRequest request,
                                             Authentication authentication) {
        return attendanceService.markAttendance(request, authentication.getName());
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public List<AttendanceResponse> getAttendanceByStudentId(@PathVariable Long studentId) {
        return attendanceService.getAttendanceByStudentId(studentId);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public List<AttendanceResponse> getMyAttendance(Authentication authentication) {
        return attendanceService.getMyAttendance(authentication.getName());
    }
}
