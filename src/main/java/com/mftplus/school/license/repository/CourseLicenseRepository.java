package com.mftplus.school.license.repository;

import com.mftplus.school.license.model.CourseLicense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseLicenseRepository extends JpaRepository<CourseLicense, Long> {

    @Query(
            value = "SELECT * FROM course_licenses WHERE deleted = true",
            countQuery = "SELECT count(*) FROM course_licenses WHERE deleted = true",
            nativeQuery = true
    )
    Page<CourseLicense> findAllSoftDeletedLicenses(Pageable pageable);

    @Query(
            value = "SELECT * FROM course_licenses",
            countQuery = "SELECT count(*) FROM course_licenses",
            nativeQuery = true
    )
    Page<CourseLicense> findAllIncludingSoftDeleted(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course_licenses SET deleted = false WHERE id = :id", nativeQuery = true)
    void restoreSoftDeletedLicenseById(@Param("id") Long id);

    Page<CourseLicense> findByStudentId(Long studentId, Pageable pageable);

    boolean existsByLicenseNumber(String licenseNumber);
}