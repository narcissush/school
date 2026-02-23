package com.mftplus.school.lesson.mapper;

import com.mftplus.school.lesson.dto.LessonDto;
import com.mftplus.school.lesson.model.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonDto toLessonDto(Lesson lesson);

    Lesson toLesson(LessonDto lessonDto);

}
