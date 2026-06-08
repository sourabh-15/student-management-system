package com.gaurav.student_management_system.Service;

import com.gaurav.student_management_system.Dto.MarkRequest;
import com.gaurav.student_management_system.Dto.MarkResponse;

import java.util.List;

public interface MarkService {
    MarkResponse addMark(MarkRequest request, String username);
    List<MarkResponse> getMarksByStudentId(Long studentId);
    List<MarkResponse> getMyMarks(String username);
}