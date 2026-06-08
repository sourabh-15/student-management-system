package com.gaurav.student_management_system.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private Double score;

    @Column(nullable = false)
    private Double maxMarks;

    @Column(nullable = false)
    private String examType;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "added_by", nullable = false)
    private User addedBy;
}
