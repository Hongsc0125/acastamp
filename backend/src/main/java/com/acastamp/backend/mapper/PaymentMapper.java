package com.acastamp.backend.mapper;

import com.acastamp.backend.domain.payment.Payment;
import com.acastamp.backend.domain.payment.Receipt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentMapper {

    // Payment CRUD
    Payment findById(@Param("id") Long id);
    List<Payment> findByStudent(@Param("studentId") Long studentId);
    List<Payment> findByStatus(@Param("academyId") Long academyId, @Param("status") String status);
    List<Payment> findOverduePayments(@Param("academyId") Long academyId);
    void insert(Payment payment);
    void update(Payment payment);
    void updateStatus(@Param("id") Long id, @Param("status") String status);
    void delete(@Param("id") Long id);

    // Receipt CRUD
    Receipt findReceiptById(@Param("id") Long id);
    Receipt findReceiptByPaymentId(@Param("paymentId") Long paymentId);
    List<Receipt> findReceiptsByAcademy(@Param("academyId") Long academyId);
    void insertReceipt(Receipt receipt);
    void deleteReceipt(@Param("id") Long id);
}
