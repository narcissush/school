package com.mftplus.school.core.service;

import com.mftplus.school.core.dto.StudentCreateDto;
import com.mftplus.school.core.dto.StudentUpdateDto;
import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.model.Student;
import com.mftplus.school.core.model.Teacher;

import java.util.List;

public interface StudentService {
    StudentCreateDto create(StudentCreateDto dto);

    StudentUpdateDto update(Long id, StudentUpdateDto dto);

    StudentUpdateDto findById(Long id);

    List<StudentUpdateDto> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    Student getReferenceById(Long id);
}

