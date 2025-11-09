package com.acastamp.backend.controller;

import com.acastamp.backend.domain.attendance.Attendance;
import com.acastamp.backend.domain.attendance.MakeupLesson;
import com.acastamp.backend.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 출석 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance", description = "출석 및 보강 관리 API")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/{id}")
    @Operation(summary = "출석 조회", description = "ID로 출석 기록을 조회합니다")
    public ResponseEntity<Attendance> getAttendance(@PathVariable Long id) {
        Attendance attendance = attendanceService.findById(id);
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "학생별 출석 조회", description = "특정 학생의 모든 출석 기록을 조회합니다")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(@PathVariable Long studentId) {
        List<Attendance> attendances = attendanceService.findByStudent(studentId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/enrollment/{enrollmentId}")
    @Operation(summary = "수강별 출석 조회", description = "특정 수강의 모든 출석 기록을 조회합니다")
    public ResponseEntity<List<Attendance>> getAttendanceByEnrollment(@PathVariable Long enrollmentId) {
        List<Attendance> attendances = attendanceService.findByEnrollment(enrollmentId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/academy/{academyId}/range")
    @Operation(summary = "기간별 출석 조회", description = "학원의 특정 기간 출석 기록을 조회합니다")
    public ResponseEntity<List<Attendance>> getAttendanceByDateRange(
            @PathVariable Long academyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Attendance> attendances = attendanceService.findByDateRange(academyId, startDate, endDate);
        return ResponseEntity.ok(attendances);
    }

    @PostMapping
    @Operation(summary = "출석 등록", description = "새로운 출석 기록을 등록합니다")
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        Attendance created = attendanceService.createAttendance(attendance);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "출석 삭제", description = "출석 기록을 삭제합니다")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok().build();
    }

    // MakeupLesson Endpoints

    @GetMapping("/makeup/{id}")
    @Operation(summary = "보강 조회", description = "ID로 보강 정보를 조회합니다")
    public ResponseEntity<MakeupLesson> getMakeup(@PathVariable Long id) {
        MakeupLesson makeup = attendanceService.findMakeupById(id);
        return ResponseEntity.ok(makeup);
    }

    @GetMapping("/makeup/student/{studentId}")
    @Operation(summary = "학생별 보강 조회", description = "특정 학생의 모든 보강 정보를 조회합니다")
    public ResponseEntity<List<MakeupLesson>> getMakeupsByStudent(@PathVariable Long studentId) {
        List<MakeupLesson> makeups = attendanceService.findMakeupsByStudent(studentId);
        return ResponseEntity.ok(makeups);
    }

    @GetMapping("/makeup/academy/{academyId}/status/{status}")
    @Operation(summary = "상태별 보강 조회", description = "학원의 특정 상태 보강을 조회합니다 (PENDING, SCHEDULED, COMPLETED)")
    public ResponseEntity<List<MakeupLesson>> getMakeupsByStatus(
            @PathVariable Long academyId,
            @PathVariable String status) {
        List<MakeupLesson> makeups = attendanceService.findMakeupsByStatus(academyId, status);
        return ResponseEntity.ok(makeups);
    }

    @PostMapping("/makeup")
    @Operation(summary = "보강 등록", description = "새로운 보강을 등록합니다")
    public ResponseEntity<MakeupLesson> createMakeup(@RequestBody MakeupLesson makeupLesson) {
        MakeupLesson created = attendanceService.createMakeup(makeupLesson);
        return ResponseEntity.ok(created);
    }

    @PatchMapping("/makeup/{id}/status")
    @Operation(summary = "보강 상태 변경", description = "보강의 상태를 변경합니다")
    public ResponseEntity<Void> updateMakeupStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        attendanceService.updateMakeupStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/makeup/{id}")
    @Operation(summary = "보강 삭제", description = "보강을 삭제합니다")
    public ResponseEntity<Void> deleteMakeup(@PathVariable Long id) {
        attendanceService.deleteMakeup(id);
        return ResponseEntity.ok().build();
    }
}
