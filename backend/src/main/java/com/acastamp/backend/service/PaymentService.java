package com.acastamp.backend.service;

import com.acastamp.backend.domain.payment.Payment;
import com.acastamp.backend.domain.payment.Receipt;
import com.acastamp.backend.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 결제 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentMapper paymentMapper;

    // Payment CRUD

    public Payment findById(Long id) {
        return paymentMapper.findById(id);
    }

    public List<Payment> findByStudent(Long studentId) {
        return paymentMapper.findByStudent(studentId);
    }

    public List<Payment> findByStatus(Long academyId, String status) {
        return paymentMapper.findByStatus(academyId, status);
    }

    public List<Payment> findOverduePayments(Long academyId) {
        return paymentMapper.findOverduePayments(academyId);
    }

    @Transactional
    public Payment createPayment(Payment payment) {
        if (payment.getStatus() == null) {
            payment.setStatus("PENDING");
        }
        paymentMapper.insert(payment);
        return payment;
    }

    @Transactional
    public Payment updatePayment(Payment payment) {
        paymentMapper.update(payment);
        return payment;
    }

    @Transactional
    public void updatePaymentStatus(Long id, String status) {
        paymentMapper.updateStatus(id, status);
    }

    @Transactional
    public void deletePayment(Long id) {
        paymentMapper.delete(id);
    }

    // Receipt CRUD

    public Receipt findReceiptById(Long id) {
        return paymentMapper.findReceiptById(id);
    }

    public Receipt findReceiptByPaymentId(Long paymentId) {
        return paymentMapper.findReceiptByPaymentId(paymentId);
    }

    public List<Receipt> findReceiptsByAcademy(Long academyId) {
        return paymentMapper.findReceiptsByAcademy(academyId);
    }

    @Transactional
    public Receipt createReceipt(Receipt receipt) {
        // 영수증 번호 자동 생성 (예: REC-20250109-0001)
        if (receipt.getReceiptNumber() == null) {
            String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String receiptNumber = "REC-" + dateStr + "-" + String.format("%04d", System.currentTimeMillis() % 10000);
            receipt.setReceiptNumber(receiptNumber);
        }

        if (receipt.getIssueDate() == null) {
            receipt.setIssueDate(LocalDate.now());
        }

        if (receipt.getCashReceiptIssued() == null) {
            receipt.setCashReceiptIssued(false);
        }

        paymentMapper.insertReceipt(receipt);
        return receipt;
    }

    @Transactional
    public void deleteReceipt(Long id) {
        paymentMapper.deleteReceipt(id);
    }
}
