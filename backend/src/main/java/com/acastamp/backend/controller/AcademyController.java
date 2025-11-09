package com.acastamp.backend.controller;

import com.acastamp.backend.domain.academy.Academy;
import com.acastamp.backend.domain.academy.AcademySettings;
import com.acastamp.backend.service.AcademyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academy")
@RequiredArgsConstructor
@Tag(name = "Academy API", description = "학원 관리 API")
public class AcademyController {

    private final AcademyService academyService;

    @GetMapping
    @Operation(summary = "모든 학원 조회", description = "등록된 모든 학원을 조회합니다.")
    public ResponseEntity<List<Academy>> getAllAcademies() {
        return ResponseEntity.ok(academyService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "학원 상세 조회", description = "학원 ID로 상세 정보를 조회합니다.")
    public ResponseEntity<Academy> getAcademy(@PathVariable Long id) {
        Academy academy = academyService.findById(id);
        return academy != null ? ResponseEntity.ok(academy) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "학원 등록", description = "새로운 학원을 등록합니다.")
    public ResponseEntity<Academy> createAcademy(@RequestBody Academy academy) {
        Academy created = academyService.create(academy);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "학원 정보 수정", description = "학원 정보를 수정합니다.")
    public ResponseEntity<Void> updateAcademy(@PathVariable Long id, @RequestBody Academy academy) {
        academy.setId(id);
        academyService.update(academy);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "학원 삭제", description = "학원을 삭제합니다.")
    public ResponseEntity<Void> deleteAcademy(@PathVariable Long id) {
        academyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{academyId}/settings")
    @Operation(summary = "학원 시스템 설정 조회", description = "학원의 시스템 설정을 조회합니다.")
    public ResponseEntity<AcademySettings> getSettings(@PathVariable Long academyId) {
        AcademySettings settings = academyService.getSettings(academyId);
        return settings != null ? ResponseEntity.ok(settings) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{academyId}/settings")
    @Operation(summary = "학원 시스템 설정 수정", description = "학원의 시스템 설정을 수정합니다.")
    public ResponseEntity<Void> updateSettings(@PathVariable Long academyId, @RequestBody AcademySettings settings) {
        settings.setAcademyId(academyId);
        academyService.updateSettings(settings);
        return ResponseEntity.ok().build();
    }
}
