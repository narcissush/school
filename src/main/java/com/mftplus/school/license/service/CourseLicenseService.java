package com.mftplus.school.license.service;

import com.mftplus.school.license.dto.CourseLicenseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseLicenseService {

    void save(CourseLicenseDto courseLicenseDto);

    void update(CourseLicenseDto courseLicenseDto);

    void deleteById(Long id);

    CourseLicenseDto findById(Long id);

    List<CourseLicenseDto> findAll();

    Page<CourseLicenseDto> findAll(Pageable pageable);

    Page<CourseLicenseDto> findAllSoftDeleted(Pageable pageable);

    Page<CourseLicenseDto> findAllIncludingSoftDeleted(Pageable pageable);

    void restoreSoftDeletedById(Long id);

    Page<CourseLicenseDto> findByStudentId(Long studentId, Pageable pageable);

    boolean existsByLicenseNumber(String licenseNumber);

}