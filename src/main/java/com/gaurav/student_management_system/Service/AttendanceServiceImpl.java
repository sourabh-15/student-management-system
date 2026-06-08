package com.gaurav.student_management_system.Service;


import com.gaurav.student_management_system.Dto.AttendanceRequest;
import com.gaurav.student_management_system.Dto.AttendanceResponse;
import com.gaurav.student_management_system.Entity.Attendance;
import com.gaurav.student_management_system.Entity.Student;
import com.gaurav.student_management_system.Entity.User;
import com.gaurav.student_management_system.Exception.ResourceNotFoundException;
import com.gaurav.student_management_system.Repository.AttendanceRepository;
import com.gaurav.student_management_system.Repository.StudentRepository;
import com.gaurav.student_management_system.Repository.UserRepository;
import com.gaurav.student_management_system.Service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    public AttendanceResponse markAttendance(AttendanceRequest request, String username) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        User teacher = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Logged in user not found"));

        Attendance attendance = Attendance.builder()
                .attendanceDate(request.getAttendanceDate())
                .present(request.getPresent())
                .student(student)
                .markedBy(teacher)
                .build();

        return mapToResponse(attendanceRepository.save(attendance));
    }

    @Override
    public List<AttendanceResponse> getAttendanceByStudentId(Long studentId) {
        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AttendanceResponse> getMyAttendance(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not linked with current user"));

        return attendanceRepository.findByStudentId(student.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AttendanceResponse mapToResponse(Attendance attendance) {
        return AttendanceResponse.builder()
                .id(attendance.getId())
                .attendanceDate(attendance.getAttendanceDate())
                .present(attendance.getPresent())
                .studentId(attendance.getStudent().getId())
                .studentName(attendance.getStudent().getFullName())
                .markedBy(attendance.getMarkedBy().getUsername())
                .build();
    }
}
