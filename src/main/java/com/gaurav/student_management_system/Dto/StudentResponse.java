package com.gaurav.student_management_system.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String rollNumber;
    private String department;
    private String course;
    private Integer semester;
    private String username;
}
