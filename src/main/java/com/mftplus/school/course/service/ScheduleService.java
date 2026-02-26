package com.mftplus.school.course.service;

import com.mftplus.school.course.dto.ScheduleCreateDto;
import com.mftplus.school.course.dto.ScheduleUpdateDto;

import java.util.List;

public interface ScheduleService {
    ScheduleCreateDto create(ScheduleCreateDto dto);

    ScheduleUpdateDto update(Long id, ScheduleUpdateDto dto);

    ScheduleUpdateDto findById(Long id);

    List<ScheduleUpdateDto> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<ScheduleUpdateDto> findFreeSchedulesByTeacher(Long teacherId);


}
