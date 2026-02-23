package com.mftplus.school.course.service;

import com.mftplus.school.core.model.Student;
import com.mftplus.school.core.model.Teacher;
import com.mftplus.school.core.repository.StudentRepository;
import com.mftplus.school.core.repository.TeacherRepository;
import com.mftplus.school.course.dto.CourseCreateDto;
import com.mftplus.school.course.dto.CourseUpdateDto;
import com.mftplus.school.course.exception.ResourceNotFoundException;
import com.mftplus.school.course.mapper.CourseMapper;
import com.mftplus.school.course.model.Course;
import com.mftplus.school.course.model.Schedule;
import com.mftplus.school.course.repository.CourseRepository;
import com.mftplus.school.course.repository.ScheduleRepository;
import com.mftplus.school.lesson.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImp implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final ScheduleRepository scheduleRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseCreateDto create(CourseCreateDto dto) {
        Course course = courseMapper.toEntity(dto);

        // Set Teacher
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("استاد یافت نشد با id: " + dto.getTeacherId()));
        course.setTeacher(teacher);

        // Set Students
        if (dto.getStudentIds() != null && !dto.getStudentIds().isEmpty()) {
            List<Student> students = studentRepository.findAllById(dto.getStudentIds());
            course.setStudents(students);
        }

        // Set Lesson
        if (dto.getLessonId() != null) {
            course.setLesson(lessonRepository.findById(dto.getLessonId())
                    .orElseThrow(() -> new ResourceNotFoundException("درس یافت نشد با id: " + dto.getLessonId())));
        }

        // Set CourseSchedule
        if (dto.getCourseScheduleId() != null) {
            Schedule schedule = scheduleRepository.findById(dto.getCourseScheduleId())
                    .orElseThrow(() -> new ResourceNotFoundException("زمان‌بندی یافت نشد با id: " + dto.getCourseScheduleId()));
            course.setSchedule(schedule);
            schedule.setCourse(course);
        }

        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }

    @Override
    public CourseUpdateDto update(Long id, CourseUpdateDto dto) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("دوره یافت نشد با id: " + id));

        // فقط فیلدهای قابل تغییر
        Course updatedEntity = courseMapper.toUpdateEntity(dto);

        if (dto.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("استاد یافت نشد با id: " + dto.getTeacherId()));
            updatedEntity.setTeacher(teacher);
        } else {
            updatedEntity.setTeacher(existing.getTeacher());
        }

        if (dto.getStudentIds() != null) {
            List<Student> students = studentRepository.findAllById(dto.getStudentIds());
            updatedEntity.setStudents(students);
        } else {
            updatedEntity.setStudents(existing.getStudents());
        }

        if (dto.getLessonId() != null) {
            updatedEntity.setLesson(lessonRepository.findById(dto.getLessonId())
                    .orElseThrow(() -> new ResourceNotFoundException("درس یافت نشد با id: " + dto.getLessonId())));
        } else {
            updatedEntity.setLesson(existing.getLesson());
        }

        if (dto.getCourseScheduleId() != null) {
            Schedule schedule = scheduleRepository.findById(dto.getCourseScheduleId())
                    .orElseThrow(() -> new ResourceNotFoundException("زمان‌بندی یافت نشد با id: " + dto.getCourseScheduleId()));
            updatedEntity.setSchedule(schedule);
            schedule.setCourse(updatedEntity);
        } else {
            updatedEntity.setSchedule(existing.getSchedule());
        }

        updatedEntity.setId(existing.getId());
        Course saved = courseRepository.save(updatedEntity);

        return courseMapper.toUpdateDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseUpdateDto findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("دوره یافت نشد با id: " + id));
        return courseMapper.toUpdateDto(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseUpdateDto> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toUpdateDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("دوره یافت نشد با id: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }
}