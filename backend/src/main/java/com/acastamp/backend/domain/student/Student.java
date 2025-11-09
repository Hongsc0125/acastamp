package com.acastamp.backend.domain.student;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 학생 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    private Long id;
    private Long academyId;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private String parentPhone;
    private String school;
    private String grade;
    private String status; // ACTIVE, DORMANT, WITHDRAWN
    private LocalDate enrollmentDate;
    private LocalDate dormantDate;
    private LocalDate withdrawalDate;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
