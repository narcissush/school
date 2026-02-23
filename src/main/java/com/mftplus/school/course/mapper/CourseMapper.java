package com.mftplus.school.course.mapper;

import com.mftplus.school.course.dto.CourseCreateDto;
import com.mftplus.school.course.dto.CourseUpdateDto;
import com.mftplus.school.course.model.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    public Course toEntity(CourseCreateDto courseCreateDto);
    public CourseCreateDto toDto(Course course) ;
    public Course toUpdateEntity(CourseUpdateDto courseUpdateDto) ;
    public CourseUpdateDto toUpdateDto(Course course) ;
}
