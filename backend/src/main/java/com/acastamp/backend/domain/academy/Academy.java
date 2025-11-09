package com.acastamp.backend.domain.academy;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 학원 정보 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Academy {
    private Long id;
    private String name;
    private String businessNumber;
    private String representativeName;
    private String phone;
    private String address;
    private String bankName;
    private String accountNumber;
    private String accountHolder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
