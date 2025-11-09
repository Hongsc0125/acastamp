package com.acastamp.backend.domain.attendance;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 보강 예약 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MakeupLesson {
    private Long id;
    private Long academyId;
    private Long studentId;
    private Long enrollmentId;
    private LocalDate originalDate;
    private LocalDate makeupDate;
    private LocalTime makeupTime;
    private String status; // SCHEDULED, COMPLETED, CANCELLED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
