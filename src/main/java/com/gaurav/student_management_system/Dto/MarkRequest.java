package com.gaurav.student_management_system.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MarkRequest {

    @NotBlank
    private String subject;

    @NotNull
    private Double score;

    @NotNull
    private Double maxMarks;

    @NotBlank
    private String examType;

    @NotNull
    private Long studentId;
}
