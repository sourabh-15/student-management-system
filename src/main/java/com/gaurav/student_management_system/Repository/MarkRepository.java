package com.gaurav.student_management_system.Repository;

import com.gaurav.student_management_system.Entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findByStudentId(Long studentId);
}
