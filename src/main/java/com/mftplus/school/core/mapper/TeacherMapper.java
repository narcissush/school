package com.mftplus.school.core.mapper;

import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "lessonList", ignore = true)
    @Mapping(target = "courseList", ignore = true)
    @Mapping(target = "courseScheduleList", ignore = true)
    @Mapping(target = "experienceList", ignore = true)
    @Mapping(target = "teacherList", ignore = true)
    Teacher toCreateEntity(TeacherCreateDto dto);

    TeacherCreateDto toCreateDto(Teacher teacher);

    @Mapping(target = "lessonList", ignore = true)
    @Mapping(target = "courseList", ignore = true)
    @Mapping(target = "courseScheduleList", ignore = true)
    @Mapping(target = "experienceList", ignore = true)
    @Mapping(target = "teacherList", ignore = true)
    Teacher toUpdateEntity(TeacherUpdateDto dto);

    TeacherUpdateDto toUpdateDto(Teacher teacher);
}
