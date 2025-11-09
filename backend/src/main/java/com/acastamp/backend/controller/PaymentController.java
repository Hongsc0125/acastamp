package com.acastamp.backend.controller;

import com.acastamp.backend.domain.payment.Payment;
import com.acastamp.backend.domain.payment.Receipt;
import com.acastamp.backend.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 결제 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "결제 및 영수증 관리 API")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    @Operation(summary = "결제 조회", description = "ID로 결제 정보를 조회합니다")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "학생별 결제 조회", description = "특정 학생의 모든 결제 내역을 조회합니다")
    public ResponseEntity<List<Payment>> getPaymentsByStudent(@PathVariable Long studentId) {
        List<Payment> payments = paymentService.findByStudent(studentId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/academy/{academyId}/status/{status}")
    @Operation(summary = "상태별 결제 조회", description = "학원의 특정 상태 결제를 조회합니다 (PENDING, COMPLETED, OVERDUE)")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(
            @PathVariable Long academyId,
            @PathVariable String status) {
        List<Payment> payments = paymentService.findByStatus(academyId, status);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/academy/{academyId}/overdue")
    @Operation(summary = "연체 결제 조회", description = "학원의 모든 연체 결제를 조회합니다")
    public ResponseEntity<List<Payment>> getOverduePayments(@PathVariable Long academyId) {
        List<Payment> payments = paymentService.findOverduePayments(academyId);
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    @Operation(summary = "결제 등록", description = "새로운 결제를 등록합니다")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment created = paymentService.createPayment(payment);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "결제 수정", description = "결제 정보를 수정합니다")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        payment.setId(id);
        Payment updated = paymentService.updatePayment(payment);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "결제 상태 변경", description = "결제 상태를 변경합니다")
    public ResponseEntity<Void> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "결제 삭제", description = "결제를 삭제합니다")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok().build();
    }

    // Receipt Endpoints

    @GetMapping("/receipt/{id}")
    @Operation(summary = "영수증 조회", description = "ID로 영수증을 조회합니다")
    public ResponseEntity<Receipt> getReceipt(@PathVariable Long id) {
        Receipt receipt = paymentService.findReceiptById(id);
        return ResponseEntity.ok(receipt);
    }

    @GetMapping("/receipt/payment/{paymentId}")
    @Operation(summary = "결제별 영수증 조회", description = "특정 결제의 영수증을 조회합니다")
    public ResponseEntity<Receipt> getReceiptByPayment(@PathVariable Long paymentId) {
        Receipt receipt = paymentService.findReceiptByPaymentId(paymentId);
        return ResponseEntity.ok(receipt);
    }

    @GetMapping("/receipt/academy/{academyId}")
    @Operation(summary = "학원별 영수증 조회", description = "학원의 모든 영수증을 조회합니다")
    public ResponseEntity<List<Receipt>> getReceiptsByAcademy(@PathVariable Long academyId) {
        List<Receipt> receipts = paymentService.findReceiptsByAcademy(academyId);
        return ResponseEntity.ok(receipts);
    }

    @PostMapping("/receipt")
    @Operation(summary = "영수증 발급", description = "새로운 영수증을 발급합니다")
    public ResponseEntity<Receipt> createReceipt(@RequestBody Receipt receipt) {
        Receipt created = paymentService.createReceipt(receipt);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/receipt/{id}")
    @Operation(summary = "영수증 삭제", description = "영수증을 삭제합니다")
    public ResponseEntity<Void> deleteReceipt(@PathVariable Long id) {
        paymentService.deleteReceipt(id);
        return ResponseEntity.ok().build();
    }
}
