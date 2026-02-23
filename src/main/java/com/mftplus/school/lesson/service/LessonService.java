package com.mftplus.school.lesson.service;

import com.mftplus.school.lesson.dto.LessonDto;
import com.mftplus.school.lesson.model.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {
    Lesson save(LessonDto lessonDto);
    Lesson update(LessonDto lessonDto);
    void  DeleteById(Long id);
    Optional<Lesson> findById(Long id);
    Optional<Lesson> findByLessonName(String lessonName);
    List<Lesson> findAll();
}
