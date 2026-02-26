package com.mftplus.school.license.service;

import com.mftplus.school.core.model.Student;
import com.mftplus.school.core.repository.StudentRepository;
import com.mftplus.school.license.dto.CourseLicenseDto;
import com.mftplus.school.license.mapper.CourseLicenseMapper;
import com.mftplus.school.license.model.CourseLicense;
import com.mftplus.school.license.repository.CourseLicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseLicenseServiceImpl implements CourseLicenseService {

    private final CourseLicenseRepository courseLicenseRepository;
    private final CourseLicenseMapper courseLicenseMapper;

    private final StudentRepository studentRepository;

    @Transactional
    @Override
    public void save(CourseLicenseDto courseLicenseDto) {
        CourseLicense entity = courseLicenseMapper.toEntity(courseLicenseDto);

        Student student = studentRepository.findById(courseLicenseDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        entity.setStudent(student);

        courseLicenseRepository.save(entity);
    }

    @Transactional
    @Override
    public void update(CourseLicenseDto courseLicenseDto) {
        if (courseLicenseDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }

        CourseLicense entity = courseLicenseMapper.toEntity(courseLicenseDto);

        Student student = studentRepository.findById(courseLicenseDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        entity.setStudent(student);

        courseLicenseRepository.save(entity);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        courseLicenseRepository.deleteById(id);
    }

    @Override
    public CourseLicenseDto findById(Long id) {
        return courseLicenseRepository.findById(id)
                .map(courseLicenseMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<CourseLicenseDto> findAll() {
        return courseLicenseRepository.findAll()
                .stream()
                .map(courseLicenseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CourseLicenseDto> findAll(Pageable pageable) {
        return courseLicenseRepository.findAll(pageable)
                .map(courseLicenseMapper::toDto);
    }

    @Override
    public Page<CourseLicenseDto> findAllSoftDeleted(Pageable pageable) {
        return courseLicenseRepository.findAllSoftDeletedLicenses(pageable)
                .map(courseLicenseMapper::toDto);
    }

    @Override
    public Page<CourseLicenseDto> findAllIncludingSoftDeleted(Pageable pageable) {
        return courseLicenseRepository.findAllIncludingSoftDeleted(pageable)
                .map(courseLicenseMapper::toDto);
    }

    @Transactional
    @Override
    public void restoreSoftDeletedById(Long id) {
        courseLicenseRepository.restoreSoftDeletedLicenseById(id);
    }

    @Override
    public Page<CourseLicenseDto> findByStudentId(Long studentId, Pageable pageable) {
        return courseLicenseRepository.findByStudentId(studentId, pageable)
                .map(courseLicenseMapper::toDto);
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        return courseLicenseRepository.existsByLicenseNumber(licenseNumber);
    }
}