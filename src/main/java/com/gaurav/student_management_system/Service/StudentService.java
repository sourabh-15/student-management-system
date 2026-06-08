package com.gaurav.student_management_system.Service;

import com.gaurav.student_management_system.Dto.StudentRequest;
import com.gaurav.student_management_system.Dto.StudentResponse;

import java.util.List;

public interface StudentService {
    StudentResponse createStudent(StudentRequest request);
    StudentResponse updateStudent(Long id, StudentRequest request);
    void deleteStudent(Long id);
    StudentResponse getStudentById(Long id);
    List<StudentResponse> getAllStudents();
    StudentResponse getMyProfile(String username);
}