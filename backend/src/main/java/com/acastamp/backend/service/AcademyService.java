package com.acastamp.backend.service;

import com.acastamp.backend.domain.academy.Academy;
import com.acastamp.backend.domain.academy.AcademySettings;
import com.acastamp.backend.mapper.AcademyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademyService {

    private final AcademyMapper academyMapper;

    /**
     * 학원 ID로 조회
     */
    public Academy findById(Long id) {
        return academyMapper.findById(id);
    }

    /**
     * 모든 학원 조회
     */
    public List<Academy> findAll() {
        return academyMapper.findAll();
    }

    /**
     * 학원 등록
     */
    @Transactional
    public Academy create(Academy academy) {
        academyMapper.insert(academy);

        // 학원 생성 시 기본 설정 자동 생성
        AcademySettings settings = AcademySettings.builder()
                .academyId(academy.getId())
                .attendanceMethod("KEYPAD")
                .keypadAuthType("PASSWORD")
                .defaultMakeupLimit(2)
                .build();
        academyMapper.insertSettings(settings);

        return academy;
    }

    /**
     * 학원 정보 수정
     */
    @Transactional
    public void update(Academy academy) {
        academyMapper.update(academy);
    }

    /**
     * 학원 삭제
     */
    @Transactional
    public void delete(Long id) {
        academyMapper.delete(id);
    }

    /**
     * 학원 시스템 설정 조회
     */
    public AcademySettings getSettings(Long academyId) {
        return academyMapper.findSettingsByAcademyId(academyId);
    }

    /**
     * 학원 시스템 설정 수정
     */
    @Transactional
    public void updateSettings(AcademySettings settings) {
        academyMapper.updateSettings(settings);
    }
}
