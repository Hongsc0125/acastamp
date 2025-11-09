package com.acastamp.backend.service;

import com.acastamp.backend.domain.lesson.LessonType;
import com.acastamp.backend.domain.lesson.PaymentMethod;
import com.acastamp.backend.mapper.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonMapper lessonMapper;

    // LessonType 관리
    public LessonType findLessonTypeById(Long id) {
        return lessonMapper.findLessonTypeById(id);
    }

    public List<LessonType> findAllLessonTypes(Long academyId) {
        return lessonMapper.findAllLessonTypes(academyId);
    }

    public List<LessonType> findActiveLessonTypes(Long academyId) {
        return lessonMapper.findActiveLessonTypes(academyId);
    }

    @Transactional
    public LessonType createLessonType(LessonType lessonType) {
        lessonMapper.insertLessonType(lessonType);
        return lessonType;
    }

    @Transactional
    public void updateLessonType(LessonType lessonType) {
        lessonMapper.updateLessonType(lessonType);
    }

    @Transactional
    public void deleteLessonType(Long id) {
        lessonMapper.deleteLessonType(id);
    }

    // PaymentMethod 관리
    public PaymentMethod findPaymentMethodById(Long id) {
        return lessonMapper.findPaymentMethodById(id);
    }

    public List<PaymentMethod> findAllPaymentMethods(Long academyId) {
        return lessonMapper.findAllPaymentMethods(academyId);
    }

    public List<PaymentMethod> findActivePaymentMethods(Long academyId) {
        return lessonMapper.findActivePaymentMethods(academyId);
    }

    @Transactional
    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        lessonMapper.insertPaymentMethod(paymentMethod);
        return paymentMethod;
    }

    @Transactional
    public void updatePaymentMethod(PaymentMethod paymentMethod) {
        lessonMapper.updatePaymentMethod(paymentMethod);
    }

    @Transactional
    public void deletePaymentMethod(Long id) {
        lessonMapper.deletePaymentMethod(id);
    }
}
