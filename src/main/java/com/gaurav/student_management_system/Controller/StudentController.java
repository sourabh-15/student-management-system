package com.gaurav.student_management_system.Controller;

import com.gaurav.student_management_system.Dto.StudentRequest;
import com.gaurav.student_management_system.Dto.StudentResponse;
import com.gaurav.student_management_system.Service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StudentResponse createStudent(@Valid @RequestBody StudentRequest request) {
        return studentService.createStudent(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StudentResponse updateStudent(@PathVariable Long id,
                                         @Valid @RequestBody StudentRequest request) {
        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public StudentResponse getMyProfile(Authentication authentication) {
        return studentService.getMyProfile(authentication.getName());
    }
}
