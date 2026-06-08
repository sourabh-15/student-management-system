package com.gaurav.student_management_system.Dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentRequest {

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String rollNumber;

    @NotBlank
    private String department;

    @NotBlank
    private String course;

    @NotNull
    private Integer semester;

    private Long userId;
}
