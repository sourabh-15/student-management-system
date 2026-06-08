package com.gaurav.student_management_system.Repository;

import com.gaurav.student_management_system.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
//    Optional<Student> findByEmail(String email);
//    Optional<Student> findByRollNumber(String rollNumber);
    Optional<Student> findByUserId(Long userId);
    boolean existsByEmail(String email);
    boolean existsByRollNumber(String rollNumber);
}
