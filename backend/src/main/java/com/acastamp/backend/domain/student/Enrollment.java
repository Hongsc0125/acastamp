package com.acastamp.backend.domain.student;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 수강 등록 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    private Long id;
    private Long academyId;
    private Long studentId;
    private Long lessonTypeId;
    private Long instructorId;
    private Long classroomId;
    private Integer totalCount;
    private Integer usedCount;
    private Integer remainingCount;
    private BigDecimal monthlyFee;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
