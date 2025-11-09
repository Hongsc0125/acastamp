package com.acastamp.backend.service;

import com.acastamp.backend.domain.student.Student;
import com.acastamp.backend.domain.student.Enrollment;
import com.acastamp.backend.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentMapper studentMapper;

    // Student 관리
    public Student findById(Long id) {
        return studentMapper.findById(id);
    }

    public List<Student> findAll(Long academyId) {
        return studentMapper.findAll(academyId);
    }

    public List<Student> findByStatus(Long academyId, String status) {
        return studentMapper.findByStatus(academyId, status);
    }

    @Transactional
    public Student create(Student student) {
        if (student.getStatus() == null) {
            student.setStatus("ACTIVE");
        }
        studentMapper.insert(student);
        return student;
    }

    @Transactional
    public void update(Student student) {
        studentMapper.update(student);
    }

    @Transactional
    public void updateStatus(Long id, String status) {
        studentMapper.updateStatus(id, status);
    }

    @Transactional
    public void delete(Long id) {
        studentMapper.delete(id);
    }

    // Enrollment 관리
    public Enrollment findEnrollmentById(Long id) {
        return studentMapper.findEnrollmentById(id);
    }

    public List<Enrollment> findEnrollmentsByStudent(Long studentId) {
        return studentMapper.findEnrollmentsByStudent(studentId);
    }

    public List<Enrollment> findActiveEnrollmentsByStudent(Long studentId) {
        return studentMapper.findActiveEnrollmentsByStudent(studentId);
    }

    @Transactional
    public Enrollment createEnrollment(Enrollment enrollment) {
        // 초기 회차 설정
        if (enrollment.getUsedCount() == null) {
            enrollment.setUsedCount(0);
        }
        if (enrollment.getRemainingCount() == null) {
            enrollment.setRemainingCount(enrollment.getTotalCount());
        }
        studentMapper.insertEnrollment(enrollment);
        return enrollment;
    }

    @Transactional
    public void updateEnrollment(Enrollment enrollment) {
        studentMapper.updateEnrollment(enrollment);
    }

    @Transactional
    public void updateUsedCount(Long enrollmentId, Integer usedCount, Integer remainingCount) {
        studentMapper.updateUsedCount(enrollmentId, usedCount, remainingCount);
    }

    @Transactional
    public void deleteEnrollment(Long id) {
        studentMapper.deleteEnrollment(id);
    }
}
