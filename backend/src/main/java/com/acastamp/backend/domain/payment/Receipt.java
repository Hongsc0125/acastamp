package com.acastamp.backend.domain.payment;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 영수증 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {
    private Long id;
    private Long academyId;
    private Long paymentId;
    private String receiptNumber;
    private LocalDate issueDate;
    private String recipientName;
    private BigDecimal amount;
    private Boolean cashReceiptIssued;
    private String cashReceiptNumber;
    private String pdfPath;
    private LocalDateTime createdAt;
}
