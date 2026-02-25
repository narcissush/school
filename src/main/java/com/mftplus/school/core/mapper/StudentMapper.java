package com.mftplus.school.core.mapper;

import com.mftplus.school.core.dto.StudentCreateDto;
import com.mftplus.school.core.dto.StudentUpdateDto;
import com.mftplus.school.core.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    public Student toCreateEntity(StudentCreateDto studentCreateDto);
    public StudentCreateDto toCreateDto(Student student) ;
    public Student toUpdateEntity(StudentUpdateDto studentUpdateDto) ;
    public StudentUpdateDto toUpdateDto(Student student) ;

}
