package com.mftplus.school.experience.service;

import com.mftplus.school.core.service.TeacherService;
import com.mftplus.school.experience.dto.WorkExperienceDto;
import com.mftplus.school.experience.mapper.WorkExperienceMapper;
import com.mftplus.school.experience.model.WorkExperience;
import com.mftplus.school.experience.repository.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImp implements WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceMapper workExperienceMapper;
    private final TeacherService teacherService;

    @Transactional
    @Override
    public void save(WorkExperienceDto workExperienceDto) {
        WorkExperience workExperience = workExperienceMapper.toEntity(workExperienceDto);
        workExperienceRepository.save(workExperience);
    }

    @Transactional
    @Override
    public void update(WorkExperienceDto workExperienceDto) {

        if (workExperienceDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }
        WorkExperience workExperience = workExperienceMapper.toEntity(workExperienceDto);
        workExperienceRepository.save(workExperience);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        workExperienceRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void restoreById(Long id) {
        workExperienceRepository.restoreById(id);
    }

    @Override
    public WorkExperienceDto findById(Long id) {
        return workExperienceRepository.findById(id)
                .map(workExperienceMapper::toDto)
                .orElse(null);
    }


    @Override
    public List<WorkExperienceDto> findAll() {
        return workExperienceRepository.findAll()
                .stream()
                .map(workExperienceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<WorkExperienceDto> findAll(Pageable pageable) {
        return workExperienceRepository.findAll(pageable)
                .map(workExperienceMapper::toDto);
    }

    @Override
    public Page<WorkExperienceDto> findAllDeleted(Pageable pageable) {
        return workExperienceRepository.findAllDeleted(pageable)
                .map(workExperienceMapper::toDto);
    }

    @Override
    public Page<WorkExperienceDto> findAllEvenDeleted(Pageable pageable) {
        return workExperienceRepository.findAllEvenDeleted(pageable)
                .map(workExperienceMapper::toDto);
    }
}
