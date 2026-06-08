package com.gaurav.student_management_system.Service;

import com.gaurav.student_management_system.Dto.StudentRequest;
import com.gaurav.student_management_system.Dto.StudentResponse;
import com.gaurav.student_management_system.Entity.Student;
import com.gaurav.student_management_system.Entity.User;
import com.gaurav.student_management_system.Exception.BadRequestException;
import com.gaurav.student_management_system.Exception.ResourceNotFoundException;
import com.gaurav.student_management_system.Repository.StudentRepository;
import com.gaurav.student_management_system.Repository.UserRepository;
import com.gaurav.student_management_system.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        if (studentRepository.existsByRollNumber(request.getRollNumber())) {
            throw new BadRequestException("Roll number already exists");
        }

        User user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        Student student = Student.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .rollNumber(request.getRollNumber())
                .department(request.getDepartment())
                .course(request.getCourse())
                .semester(request.getSemester())
                .user(user)
                .build();

        return mapToResponse(studentRepository.save(student));
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (!student.getEmail().equals(request.getEmail()) &&
                studentRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        if (!student.getRollNumber().equals(request.getRollNumber()) &&
                studentRepository.existsByRollNumber(request.getRollNumber())) {
            throw new BadRequestException("Roll number already exists");
        }

        User user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setRollNumber(request.getRollNumber());
        student.setDepartment(request.getDepartment());
        student.setCourse(request.getCourse());
        student.setSemester(request.getSemester());
        student.setUser(user);

        return mapToResponse(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        studentRepository.delete(student);
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return mapToResponse(student);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentResponse getMyProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not linked with current user"));

        return mapToResponse(student);
    }

    private StudentResponse mapToResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .fullName(student.getFullName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .rollNumber(student.getRollNumber())
                .department(student.getDepartment())
                .course(student.getCourse())
                .semester(student.getSemester())
                .username(student.getUser() != null ? student.getUser().getUsername() : null)
                .build();
    }
}
