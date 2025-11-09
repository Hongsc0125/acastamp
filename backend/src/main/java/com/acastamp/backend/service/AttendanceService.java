package com.acastamp.backend.service;

import com.acastamp.backend.domain.attendance.Attendance;
import com.acastamp.backend.domain.attendance.MakeupLesson;
import com.acastamp.backend.mapper.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 출석 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;

    // Attendance CRUD

    public Attendance findById(Long id) {
        return attendanceMapper.findById(id);
    }

    public List<Attendance> findByStudent(Long studentId) {
        return attendanceMapper.findByStudent(studentId);
    }

    public List<Attendance> findByEnrollment(Long enrollmentId) {
        return attendanceMapper.findByEnrollment(enrollmentId);
    }

    public List<Attendance> findByDateRange(Long academyId, LocalDate startDate, LocalDate endDate) {
        return attendanceMapper.findByDateRange(academyId, startDate, endDate);
    }

    @Transactional
    public Attendance createAttendance(Attendance attendance) {
        attendanceMapper.insert(attendance);
        return attendance;
    }

    @Transactional
    public void deleteAttendance(Long id) {
        attendanceMapper.delete(id);
    }

    // MakeupLesson CRUD

    public MakeupLesson findMakeupById(Long id) {
        return attendanceMapper.findMakeupById(id);
    }

    public List<MakeupLesson> findMakeupsByStudent(Long studentId) {
        return attendanceMapper.findMakeupsByStudent(studentId);
    }

    public List<MakeupLesson> findMakeupsByStatus(Long academyId, String status) {
        return attendanceMapper.findMakeupsByStatus(academyId, status);
    }

    @Transactional
    public MakeupLesson createMakeup(MakeupLesson makeupLesson) {
        if (makeupLesson.getStatus() == null) {
            makeupLesson.setStatus("PENDING");
        }
        attendanceMapper.insertMakeup(makeupLesson);
        return makeupLesson;
    }

    @Transactional
    public void updateMakeupStatus(Long id, String status) {
        attendanceMapper.updateMakeupStatus(id, status);
    }

    @Transactional
    public void deleteMakeup(Long id) {
        attendanceMapper.deleteMakeup(id);
    }
}
