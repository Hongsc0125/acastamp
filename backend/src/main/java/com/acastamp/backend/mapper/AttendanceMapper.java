package com.acastamp.backend.mapper;

import com.acastamp.backend.domain.attendance.Attendance;
import com.acastamp.backend.domain.attendance.MakeupLesson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AttendanceMapper {

    // Attendance CRUD
    Attendance findById(@Param("id") Long id);
    List<Attendance> findByStudent(@Param("studentId") Long studentId);
    List<Attendance> findByEnrollment(@Param("enrollmentId") Long enrollmentId);
    List<Attendance> findByDateRange(@Param("academyId") Long academyId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    void insert(Attendance attendance);
    void delete(@Param("id") Long id);

    // MakeupLesson CRUD
    MakeupLesson findMakeupById(@Param("id") Long id);
    List<MakeupLesson> findMakeupsByStudent(@Param("studentId") Long studentId);
    List<MakeupLesson> findMakeupsByStatus(@Param("academyId") Long academyId, @Param("status") String status);
    void insertMakeup(MakeupLesson makeupLesson);
    void updateMakeupStatus(@Param("id") Long id, @Param("status") String status);
    void deleteMakeup(@Param("id") Long id);
}
