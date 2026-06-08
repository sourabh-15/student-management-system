package com.gaurav.student_management_system.Dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarkResponse {
    private Long id;
    private String subject;
    private Double score;
    private Double maxMarks;
    private String examType;
    private Long studentId;
    private String studentName;
    private String addedBy;
}
