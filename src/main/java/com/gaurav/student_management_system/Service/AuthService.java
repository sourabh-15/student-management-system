package com.gaurav.student_management_system.Service;

import com.gaurav.student_management_system.Dto.*;

public interface AuthService {
    ApiResponse registerAdmin(RegisterRequest request);
    ApiResponse registerTeacher(RegisterRequest request);
    ApiResponse registerStudentUser(RegisterRequest request);
    JwtAuthResponse login(LoginRequest request);
}
