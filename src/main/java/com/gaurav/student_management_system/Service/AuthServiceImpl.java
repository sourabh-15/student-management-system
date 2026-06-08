package com.gaurav.student_management_system.Service;

import com.gaurav.student_management_system.Dto.*;
import com.gaurav.student_management_system.Entity.Role;
import com.gaurav.student_management_system.Entity.User;
import com.gaurav.student_management_system.Exception.BadRequestException;
import com.gaurav.student_management_system.Exception.ResourceNotFoundException;
import com.gaurav.student_management_system.Repository.UserRepository;
import com.gaurav.student_management_system.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ApiResponse registerAdmin(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);
        return new ApiResponse("Admin registered successfully");
    }

    @Override
    public ApiResponse registerTeacher(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.TEACHER)
                .build();

        userRepository.save(user);
        return new ApiResponse("Teacher registered successfully");
    }

    @Override
    public ApiResponse registerStudentUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();

        userRepository.save(user);
        return new ApiResponse("Student user registered successfully");
    }

    @Override
    public JwtAuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .build();

        String jwtToken = jwtService.generateToken(userDetails, user.getRole().name());

        return JwtAuthResponse.builder()
                .token(jwtToken)
                .tokenType("Bearer")
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
