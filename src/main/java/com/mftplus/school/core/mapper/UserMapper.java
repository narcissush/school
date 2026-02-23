package com.mftplus.school.core.mapper;

import com.mftplus.school.core.dto.UserCreateDto;
import com.mftplus.school.core.dto.UserDto;
import com.mftplus.school.core.dto.UserUpdateDto;
import com.mftplus.school.core.model.Role;
import com.mftplus.school.core.model.User;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Collectors.class})
public interface UserMapper {

    @Mapping(target = "roleNames", expression = "java(rolesToNames(user.getRoles()))")
    @Mapping(target = "personFullName", expression = "java(getPersonFullName(user))")
    @Mapping(target = "personId", expression = "java(getPersonId(user))")
    UserDto toDto(User user);

    @Mapping(target = "roles", expression = "java(namesToRoles(dto.getRoleNames()))")
    User toEntity(UserCreateDto dto);

    @Mapping(target = "roles", expression = "java(namesToRoles(dto.getRoleNames()))")
    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget User user);

    // تبدیل Set<Role> به Set<String> (اسم Enum)
    default Set<String> rolesToNames(Set<Role> roles) {
        if (roles == null) return Collections.emptySet();
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }

    // تبدیل Set<String> به Set<Role> با RoleType
    default Set<Role> namesToRoles(Set<String> names) {
        if (names == null) return Collections.emptySet();
        return names.stream().map(nameStr -> {
            Role role = new Role();
            role.setName(Role.RoleType.valueOf(nameStr));
            return role;
        }).collect(Collectors.toSet());
    }

    // این متد placeholder هست؛ Service باید implement کنه
    default String getPersonFullName(User user) {
        if (user.getRoles().stream().anyMatch(r -> r.getName() == Role.RoleType.STUDENT)) {
            // Service باید StudentRepository صدا بزنه
            return null;
        }
        if (user.getRoles().stream().anyMatch(r -> r.getName() == Role.RoleType.TEACHER)) {
            // Service باید TeacherRepository صدا بزنه
            return null;
        }
        return null;
    }

    default Long getPersonId(User user) {
        // مشابه getPersonFullName، Service باید implement کنه
        return null;
    }
}