package com.mftplus.school.course.service;

import com.mftplus.school.course.dto.CourseCreateDto;
import com.mftplus.school.course.dto.CourseUpdateDto;

import java.util.List;

public interface CourseService {

    CourseCreateDto create(CourseCreateDto dto);

    CourseUpdateDto update(Long id, CourseUpdateDto dto);

    CourseUpdateDto findById(Long id);

    List<CourseUpdateDto> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);
}
