package com.acastamp.backend.controller;

import com.acastamp.backend.domain.lesson.LessonType;
import com.acastamp.backend.domain.lesson.PaymentMethod;
import com.acastamp.backend.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
@Tag(name = "Lesson API", description = "레슨 유형 및 결제 수단 관리 API")
public class LessonController {

    private final LessonService lessonService;

    // LessonType API
    @GetMapping("/types")
    @Operation(summary = "레슨 유형 목록 조회", description = "학원의 모든 레슨 유형을 조회합니다.")
    public ResponseEntity<List<LessonType>> getAllLessonTypes(@RequestParam Long academyId) {
        return ResponseEntity.ok(lessonService.findAllLessonTypes(academyId));
    }

    @GetMapping("/types/active")
    @Operation(summary = "활성 레슨 유형 조회", description = "활성 상태인 레슨 유형만 조회합니다.")
    public ResponseEntity<List<LessonType>> getActiveLessonTypes(@RequestParam Long academyId) {
        return ResponseEntity.ok(lessonService.findActiveLessonTypes(academyId));
    }

    @GetMapping("/types/{id}")
    @Operation(summary = "레슨 유형 상세 조회")
    public ResponseEntity<LessonType> getLessonType(@PathVariable Long id) {
        LessonType lessonType = lessonService.findLessonTypeById(id);
        return lessonType != null ? ResponseEntity.ok(lessonType) : ResponseEntity.notFound().build();
    }

    @PostMapping("/types")
    @Operation(summary = "레슨 유형 등록")
    public ResponseEntity<LessonType> createLessonType(@RequestBody LessonType lessonType) {
        return ResponseEntity.ok(lessonService.createLessonType(lessonType));
    }

    @PutMapping("/types/{id}")
    @Operation(summary = "레슨 유형 수정")
    public ResponseEntity<Void> updateLessonType(@PathVariable Long id, @RequestBody LessonType lessonType) {
        lessonType.setId(id);
        lessonService.updateLessonType(lessonType);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/types/{id}")
    @Operation(summary = "레슨 유형 삭제")
    public ResponseEntity<Void> deleteLessonType(@PathVariable Long id) {
        lessonService.deleteLessonType(id);
        return ResponseEntity.ok().build();
    }

    // PaymentMethod API
    @GetMapping("/payment-methods")
    @Operation(summary = "결제 수단 목록 조회")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods(@RequestParam Long academyId) {
        return ResponseEntity.ok(lessonService.findAllPaymentMethods(academyId));
    }

    @GetMapping("/payment-methods/active")
    @Operation(summary = "활성 결제 수단 조회")
    public ResponseEntity<List<PaymentMethod>> getActivePaymentMethods(@RequestParam Long academyId) {
        return ResponseEntity.ok(lessonService.findActivePaymentMethods(academyId));
    }

    @PostMapping("/payment-methods")
    @Operation(summary = "결제 수단 등록")
    public ResponseEntity<PaymentMethod> createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        return ResponseEntity.ok(lessonService.createPaymentMethod(paymentMethod));
    }

    @PutMapping("/payment-methods/{id}")
    @Operation(summary = "결제 수단 수정")
    public ResponseEntity<Void> updatePaymentMethod(@PathVariable Long id, @RequestBody PaymentMethod paymentMethod) {
        paymentMethod.setId(id);
        lessonService.updatePaymentMethod(paymentMethod);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/payment-methods/{id}")
    @Operation(summary = "결제 수단 삭제")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        lessonService.deletePaymentMethod(id);
        return ResponseEntity.ok().build();
    }
}
