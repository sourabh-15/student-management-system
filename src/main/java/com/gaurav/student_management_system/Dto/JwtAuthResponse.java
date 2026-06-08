package com.gaurav.student_management_system.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtAuthResponse {
    private String token;
    private String tokenType;
    private String username;
    private String role;
}
