package com.gaurav.student_management_system.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false, unique = true)
    private String rollNumber;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private Integer semester;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}