package com.mftplus.school.experience.repository;

import com.mftplus.school.experience.model.WorkExperience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    @Query(
            value = "SELECT * FROM work_experiences WHERE deleted = true",
            countQuery = "SELECT count(*) FROM work_experiences WHERE deleted = true",
            nativeQuery = true
    )
    Page<WorkExperience> findAllDeleted(Pageable pageable);

    @Query(
            value = "SELECT * FROM work_experiences",
            countQuery = "SELECT count(*) FROM work_experiences",
            nativeQuery = true
    )
    Page<WorkExperience> findAllEvenDeleted(Pageable pageable);

    @Modifying
    @Query(
            value = "UPDATE work_experiences SET deleted=false WHERE id=:id",
            nativeQuery = true
    )
    void restoreById(@Param("id") Long id);

}
