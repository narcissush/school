package com.mftplus.school.lesson.service;

import com.mftplus.school.lesson.dto.LessonDto;
import com.mftplus.school.lesson.mapper.LessonMapper;
import com.mftplus.school.lesson.model.Lesson;
import com.mftplus.school.lesson.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Transactional
    @Override
    public Lesson save(LessonDto lessonDto) {
        Lesson lesson = lessonMapper.toLesson(lessonDto);
        lessonRepository.save(lesson);
        return lesson;
    }

    @Transactional
    @Override
    public Lesson update(LessonDto lessonDto) {
        if (lessonDto.getId() == null) {
            throw new IllegalArgumentException("id is required");
        }
        Lesson lesson = lessonMapper.toLesson(lessonDto);
        lessonRepository.save(lesson);
        return lesson;
    }

    @Transactional
    @Override
    public void DeleteById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("lesson id not found"));
        if (lesson.getId() != null) {
            throw new IllegalArgumentException("cannot delete lesson");
        }
        lessonRepository.deleteById(id);

    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Lesson> findById(Long id) {
        lessonRepository.findById(id).map(lessonMapper::toLessonDto);
        return lessonRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Lesson> findByLessonName(String lessonName) {
        lessonRepository.findByName(lessonName).map(lessonMapper::toLessonDto);
        return lessonRepository.findByName(lessonName);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Lesson> findAll() {
        lessonRepository.findAll().forEach(lessonMapper::toLessonDto);
        return lessonRepository.findAll();
    }
}
