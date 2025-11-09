package com.acastamp.backend.mapper;

import com.acastamp.backend.domain.lesson.LessonType;
import com.acastamp.backend.domain.lesson.PaymentMethod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LessonMapper {

    // LessonType CRUD
    LessonType findLessonTypeById(@Param("id") Long id);
    List<LessonType> findAllLessonTypes(@Param("academyId") Long academyId);
    List<LessonType> findActiveLessonTypes(@Param("academyId") Long academyId);
    void insertLessonType(LessonType lessonType);
    void updateLessonType(LessonType lessonType);
    void deleteLessonType(@Param("id") Long id);

    // PaymentMethod CRUD
    PaymentMethod findPaymentMethodById(@Param("id") Long id);
    List<PaymentMethod> findAllPaymentMethods(@Param("academyId") Long academyId);
    List<PaymentMethod> findActivePaymentMethods(@Param("academyId") Long academyId);
    void insertPaymentMethod(PaymentMethod paymentMethod);
    void updatePaymentMethod(PaymentMethod paymentMethod);
    void deletePaymentMethod(@Param("id") Long id);
}
