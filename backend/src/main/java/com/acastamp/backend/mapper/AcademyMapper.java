package com.acastamp.backend.mapper;

import com.acastamp.backend.domain.academy.Academy;
import com.acastamp.backend.domain.academy.AcademySettings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AcademyMapper {

    // Academy CRUD
    Academy findById(@Param("id") Long id);
    List<Academy> findAll();
    void insert(Academy academy);
    void update(Academy academy);
    void delete(@Param("id") Long id);

    // AcademySettings CRUD
    AcademySettings findSettingsByAcademyId(@Param("academyId") Long academyId);
    void insertSettings(AcademySettings settings);
    void updateSettings(AcademySettings settings);
}
