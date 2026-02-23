package com.mftplus.school.core.service;

import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.mapper.TeacherMapper;
import com.mftplus.school.core.model.Teacher;
import com.mftplus.school.core.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional

public class TeacherServiceImp implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public TeacherCreateDto create(TeacherCreateDto dto) {
        Teacher teacher = teacherMapper.toCreateEntity(dto);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toCreateDto(savedTeacher);
    }

    @Override
    public TeacherUpdateDto update(Long id, TeacherUpdateDto dto) {
        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

        Teacher updatedTeacher = teacherMapper.toUpdateEntity(dto);

        updatedTeacher.setId(existingTeacher.getId());
        updatedTeacher.setCreatedAt(existingTeacher.getCreatedAt());
        updatedTeacher.setDepartment(existingTeacher.getDepartment());
        updatedTeacher.setUser(existingTeacher.getUser());

        Teacher saved = teacherRepository.save(updatedTeacher);
        return teacherMapper.toUpdateDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherUpdateDto findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        return teacherMapper.toUpdateDto(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherUpdateDto> findAll() {
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::toUpdateDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return teacherRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Teacher getReferenceById(Long id) {
        // بهتر از findById وقتی فقط reference برای relation می‌خواهی
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        return teacherRepository.getReferenceById(id);
    }
}
