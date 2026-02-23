package com.mftplus.school.core.mapper;

import com.mftplus.school.core.dto.UserCreateDto;
import com.mftplus.school.core.dto.UserDto;
import com.mftplus.school.core.dto.UserUpdateDto;
import com.mftplus.school.core.model.User;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDto toDto(User user);


    User toEntity(UserCreateDto dto);


    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget User user);

}
