package com.gaurav.student_management_system.Controller;

import com.gaurav.student_management_system.Dto.*;
import com.gaurav.student_management_system.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-admin")
    public ResponseEntity<ApiResponse> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerAdmin(request));
    }

    @PostMapping("/register-teacher")
    public ResponseEntity<ApiResponse> registerTeacher(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerTeacher(request));
    }

    @PostMapping("/register-student")
    public ResponseEntity<ApiResponse> registerStudentUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerStudentUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
