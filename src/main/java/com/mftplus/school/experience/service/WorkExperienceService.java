package com.mftplus.school.experience.service;

import com.mftplus.school.experience.dto.WorkExperienceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkExperienceService {
    void save(WorkExperienceDto workExperienceDto);

    void update(WorkExperienceDto workExperienceDto);

    void deleteById(Long id);

    void restoreById(Long id);

    WorkExperienceDto findById(Long id);

    List<WorkExperienceDto> findAll();

    Page<WorkExperienceDto> findAll(Pageable pageable);

    Page<WorkExperienceDto> findAllDeleted(Pageable pageable);

    Page<WorkExperienceDto> findAllEvenDeleted(Pageable pageable);

}
