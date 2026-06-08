package com.gaurav.student_management_system.Controller;

import com.gaurav.student_management_system.Dto.MarkRequest;
import com.gaurav.student_management_system.Dto.MarkResponse;
import com.gaurav.student_management_system.Service.MarkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marks")
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public MarkResponse addMark(@Valid @RequestBody MarkRequest request, Authentication authentication) {
        return markService.addMark(request, authentication.getName());
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public List<MarkResponse> getMarksByStudentId(@PathVariable Long studentId) {
        return markService.getMarksByStudentId(studentId);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public List<MarkResponse> getMyMarks(Authentication authentication) {
        return markService.getMyMarks(authentication.getName());
    }
}
