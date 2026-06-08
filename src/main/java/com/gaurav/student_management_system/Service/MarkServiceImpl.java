package com.gaurav.student_management_system.Service;

import com.gaurav.student_management_system.Dto.MarkRequest;
import com.gaurav.student_management_system.Dto.MarkResponse;
import com.gaurav.student_management_system.Entity.Mark;
import com.gaurav.student_management_system.Entity.Student;
import com.gaurav.student_management_system.Entity.User;
import com.gaurav.student_management_system.Exception.ResourceNotFoundException;
import com.gaurav.student_management_system.Repository.MarkRepository;
import com.gaurav.student_management_system.Repository.StudentRepository;
import com.gaurav.student_management_system.Repository.UserRepository;
import com.gaurav.student_management_system.Service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    public MarkResponse addMark(MarkRequest request, String username) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        User teacher = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Logged in user not found"));

        Mark mark = Mark.builder()
                .subject(request.getSubject())
                .score(request.getScore())
                .maxMarks(request.getMaxMarks())
                .examType(request.getExamType())
                .student(student)
                .addedBy(teacher)
                .build();

        return mapToResponse(markRepository.save(mark));
    }

    @Override
    public List<MarkResponse> getMarksByStudentId(Long studentId) {
        return markRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<MarkResponse> getMyMarks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not linked with current user"));

        return markRepository.findByStudentId(student.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private MarkResponse mapToResponse(Mark mark) {
        return MarkResponse.builder()
                .id(mark.getId())
                .subject(mark.getSubject())
                .score(mark.getScore())
                .maxMarks(mark.getMaxMarks())
                .examType(mark.getExamType())
                .studentId(mark.getStudent().getId())
                .studentName(mark.getStudent().getFullName())
                .addedBy(mark.getAddedBy().getUsername())
                .build();
    }
}
