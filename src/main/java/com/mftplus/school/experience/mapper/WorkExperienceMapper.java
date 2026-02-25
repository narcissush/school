package com.mftplus.school.experience.mapper;

import com.mftplus.school.experience.dto.WorkExperienceDto;
import com.mftplus.school.experience.model.WorkExperience;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkExperienceMapper {
    WorkExperience toEntity(WorkExperienceDto workExperienceDto);

    WorkExperienceDto toDto(WorkExperience workExperience);
}
