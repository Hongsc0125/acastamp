package com.acastamp.backend.domain.lesson;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 결제 수단 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethod {
    private Long id;
    private Long academyId;
    private String name;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
