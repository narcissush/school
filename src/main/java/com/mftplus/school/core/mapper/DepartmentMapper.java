package com.mftplus.school.core.mapper;

import com.mftplus.school.core.dto.DepartmentCreateDto;
import com.mftplus.school.core.dto.DepartmentUpdateDto;
import com.mftplus.school.core.model.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    public Department toCreateEntity(DepartmentCreateDto departmentCreateDto);
    public DepartmentCreateDto toCreateDto(Department department) ;
    public Department toUpdateEntity(DepartmentUpdateDto departmentUpdateDto) ;
    public DepartmentUpdateDto toUpdateDto(Department department) ;

}
