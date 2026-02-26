package com.mftplus.school.license.mapper;

import com.mftplus.school.license.dto.CourseLicenseDto;
import com.mftplus.school.license.model.CourseLicense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseLicenseMapper {

    @Mapping(target = "studentId", source = "student.id")
    CourseLicenseDto toDto(CourseLicense courseLicense);

    @Mapping(target = "student", ignore = true) // set in service
    CourseLicense toEntity(CourseLicenseDto courseLicenseDto);
}