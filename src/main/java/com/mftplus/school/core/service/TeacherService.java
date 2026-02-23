package com.mftplus.school.core.service;

import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.model.Teacher;

import java.util.List;

public interface TeacherService {
    // Create
    TeacherCreateDto create(TeacherCreateDto dto);

    // Update
    TeacherUpdateDto update(Long id, TeacherUpdateDto dto);

    // Read
    TeacherUpdateDto findById(Long id);

    List<TeacherUpdateDto> findAll();

    // Delete
    void deleteById(Long id);

    // Business helpers
    boolean existsById(Long id);

    Teacher getReferenceById(Long id);
}

