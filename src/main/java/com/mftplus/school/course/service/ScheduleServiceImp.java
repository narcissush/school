package com.mftplus.school.course.service;

import com.mftplus.school.core.model.Teacher;
import com.mftplus.school.core.repository.TeacherRepository;
import com.mftplus.school.course.dto.ScheduleCreateDto;
import com.mftplus.school.course.dto.ScheduleUpdateDto;
import com.mftplus.school.course.exception.ResourceNotFoundException;
import com.mftplus.school.course.mapper.ScheduleMapper;
import com.mftplus.school.course.model.Schedule;
import com.mftplus.school.course.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImp implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final TeacherRepository teacherRepository;

    @Override
    public ScheduleCreateDto create(ScheduleCreateDto dto) {
        Schedule schedule = scheduleMapper.toCreateEntity(dto);

        // بررسی استاد
        teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("استاد یافت نشد با id: " + dto.getTeacherId()));

//        schedule.validateTimes(); // اعتبارسنجی زمان‌ها

        Schedule saved = scheduleRepository.save(schedule);
        return scheduleMapper.toCreateDto(saved);
    }

    @Override
    public ScheduleUpdateDto update(Long id, ScheduleUpdateDto dto) {
        Schedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("زمان‌بندی یافت نشد با id: " + id));

        Schedule updated = scheduleMapper.toUpdateEntity(dto);

        updated.setId(existing.getId());
        updated.validateTimes();

        Schedule saved = scheduleRepository.save(updated);
        return scheduleMapper.toUpdateDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleUpdateDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("زمان‌بندی یافت نشد با id: " + id));
        return scheduleMapper.toUpdateDto(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleUpdateDto> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(scheduleMapper::toUpdateDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("زمان‌بندی یافت نشد با id: " + id);
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return scheduleRepository.existsById(id);
    }

    public List<ScheduleUpdateDto> findFreeSchedulesByTeacher(Long teacherId) {
        // 1. گرفتن همه Scheduleهای استاد
        List<Schedule> schedules = scheduleRepository.findByTeacherId(teacherId);

        List<ScheduleUpdateDto> freeSchedules = new ArrayList<>();

        for (Schedule schedule : schedules) {
            // اینجا فقط Scheduleهایی که به هیچ Course متصل نیستند رو آزاد فرض می‌کنیم
            if (schedule.getCourse() == null) {
                ScheduleUpdateDto dto = new ScheduleUpdateDto();
                dto.setStartDate(schedule.getStartDate());
                dto.setEndDate(schedule.getEndDate());
                dto.setStartTime(schedule.getStartTime());
                dto.setEndTime(schedule.getEndTime());
                dto.setDaysOfWeek(schedule.getDaysOfWeek());
                freeSchedules.add(dto);
            }
        }

        return freeSchedules;
    }


}
