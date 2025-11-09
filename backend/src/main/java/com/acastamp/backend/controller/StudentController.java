package com.acastamp.backend.controller;

import com.acastamp.backend.domain.student.Student;
import com.acastamp.backend.domain.student.Enrollment;
import com.acastamp.backend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@Tag(name = "Student API", description = "학생 및 수강 관리 API")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "학생 목록 조회", description = "학원의 모든 학생을 조회합니다.")
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam Long academyId) {
        return ResponseEntity.ok(studentService.findAll(academyId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "상태별 학생 조회", description = "상태(ACTIVE, DORMANT, WITHDRAWN)별로 학생을 조회합니다.")
    public ResponseEntity<List<Student>> getStudentsByStatus(
            @RequestParam Long academyId,
            @PathVariable String status) {
        return ResponseEntity.ok(studentService.findByStatus(academyId, status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "학생 상세 조회")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "학생 등록")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.create(student));
    }

    @PutMapping("/{id}")
    @Operation(summary = "학생 정보 수정")
    public ResponseEntity<Void> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        studentService.update(student);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "학생 상태 변경", description = "재원/휴원/퇴원 상태를 변경합니다.")
    public ResponseEntity<Void> updateStudentStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        studentService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "학생 삭제")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    // Enrollment API
    @GetMapping("/{studentId}/enrollments")
    @Operation(summary = "학생 수강 목록 조회")
    public ResponseEntity<List<Enrollment>> getEnrollments(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.findEnrollmentsByStudent(studentId));
    }

    @GetMapping("/{studentId}/enrollments/active")
    @Operation(summary = "학생 활성 수강 조회")
    public ResponseEntity<List<Enrollment>> getActiveEnrollments(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.findActiveEnrollmentsByStudent(studentId));
    }

    @PostMapping("/enrollments")
    @Operation(summary = "수강 등록")
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
        return ResponseEntity.ok(studentService.createEnrollment(enrollment));
    }

    @PutMapping("/enrollments/{id}")
    @Operation(summary = "수강 정보 수정")
    public ResponseEntity<Void> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        enrollment.setId(id);
        studentService.updateEnrollment(enrollment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/enrollments/{id}")
    @Operation(summary = "수강 삭제")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        studentService.deleteEnrollment(id);
        return ResponseEntity.ok().build();
    }
}
