package com.acastamp.backend.domain.attendance;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 출석 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {
    private Long id;
    private Long academyId;
    private Long studentId;
    private Long enrollmentId;
    private LocalDate attendanceDate;
    private LocalTime attendanceTime;
    private Integer lessonNumber; // 회차
    private String type; // REGULAR, MAKEUP
    private LocalDateTime createdAt;
}
