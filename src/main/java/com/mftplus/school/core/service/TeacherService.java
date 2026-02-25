package com.mftplus.school.core.service;

import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {

    TeacherCreateDto create(TeacherCreateDto dto);

    TeacherUpdateDto update(Long id, TeacherUpdateDto dto);

    TeacherUpdateDto findById(Long id);

    List<TeacherUpdateDto> findAll();

    Page<TeacherUpdateDto> findAll(Pageable pageable);

    void deleteById(Long id);

    boolean existsById(Long id);

    Teacher getReferenceById(Long id);
}