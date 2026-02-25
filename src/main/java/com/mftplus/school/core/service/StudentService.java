package com.mftplus.school.core.service;

import com.mftplus.school.core.dto.StudentCreateDto;
import com.mftplus.school.core.dto.StudentUpdateDto;
import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.model.Student;
import com.mftplus.school.core.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    StudentCreateDto create(StudentCreateDto dto);

    StudentUpdateDto update(Long id, StudentUpdateDto dto);

    StudentUpdateDto findById(Long id);

    Page<StudentUpdateDto> findAll(Pageable pageable);

    void deleteById(Long id);

    boolean existsById(Long id);

    Student getReferenceById(Long id);
}

