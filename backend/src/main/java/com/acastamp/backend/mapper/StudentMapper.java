package com.acastamp.backend.mapper;

import com.acastamp.backend.domain.student.Student;
import com.acastamp.backend.domain.student.Enrollment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    // Student CRUD
    Student findById(@Param("id") Long id);
    List<Student> findAll(@Param("academyId") Long academyId);
    List<Student> findByStatus(@Param("academyId") Long academyId, @Param("status") String status);
    void insert(Student student);
    void update(Student student);
    void updateStatus(@Param("id") Long id, @Param("status") String status);
    void delete(@Param("id") Long id);

    // Enrollment CRUD
    Enrollment findEnrollmentById(@Param("id") Long id);
    List<Enrollment> findEnrollmentsByStudent(@Param("studentId") Long studentId);
    List<Enrollment> findActiveEnrollmentsByStudent(@Param("studentId") Long studentId);
    void insertEnrollment(Enrollment enrollment);
    void updateEnrollment(Enrollment enrollment);
    void updateUsedCount(@Param("id") Long id, @Param("usedCount") Integer usedCount, @Param("remainingCount") Integer remainingCount);
    void deleteEnrollment(@Param("id") Long id);
}
