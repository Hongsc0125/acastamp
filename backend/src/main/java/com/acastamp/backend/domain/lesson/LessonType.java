package com.acastamp.backend.domain.lesson;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 레슨 유형 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonType {
    private Long id;
    private Long academyId;
    private String name;
    private String type; // INDIVIDUAL, GROUP
    private String billingType; // COUNT_BASED, PERIOD_BASED
    private Integer lessonCount;
    private Integer periodDays;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
