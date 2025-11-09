package com.acastamp.backend.domain.academy;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 학원 시스템 설정 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademySettings {
    private Long id;
    private Long academyId;
    private String attendanceMethod; // KEYPAD, APP_PUSH
    private String keypadAuthType; // PASSWORD, PHONE_LAST_DIGITS
    private Integer defaultMakeupLimit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
