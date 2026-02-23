package com.mftplus.school.core.mapper;

import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {


    Teacher toCreateEntity(TeacherCreateDto dto);

    TeacherCreateDto toCreateDto(Teacher teacher);

    Teacher toUpdateEntity(TeacherUpdateDto dto);

    TeacherUpdateDto toUpdateDto(Teacher teacher);
}
