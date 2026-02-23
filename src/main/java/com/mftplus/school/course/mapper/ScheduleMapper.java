package com.mftplus.school.course.mapper;

import com.mftplus.school.course.dto.ScheduleCreateDto;
import com.mftplus.school.course.dto.ScheduleUpdateDto;
import com.mftplus.school.course.model.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    public Schedule toCreateEntity(ScheduleCreateDto scheduleCreateDto);
    public ScheduleCreateDto toCreateDto(Schedule schedule) ;
    public Schedule toUpdateEntity(ScheduleUpdateDto scheduleUpdateDto) ;
    public ScheduleUpdateDto toUpdateDto(Schedule schedule) ;
}
