package com.acastamp.backend.domain.payment;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 결제 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private Long id;
    private Long academyId;
    private Long studentId;
    private Long enrollmentId;
    private Long paymentMethodId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String billingMonth; // YYYY-MM
    private String status; // PAID, OVERDUE, CANCELLED
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
