package com.mftplus.school.core.service;

import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.mapper.TeacherMapper;
import com.mftplus.school.core.model.Department;
import com.mftplus.school.core.model.Teacher;
import com.mftplus.school.core.repository.DepartmentRepository;
import com.mftplus.school.core.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImp implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final DepartmentRepository departmentRepository;


    // ---------------- ایجاد استاد ----------------
    @Override
    public TeacherCreateDto create(TeacherCreateDto dto) {
        Teacher teacher = teacherMapper.toCreateEntity(dto);
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("دپارتمان یافت نشد"));

        teacher.setDepartment(department);
        Teacher saved = teacherRepository.save(teacher);
        return teacherMapper.toCreateDto(saved);
    }

    // ---------------- بروزرسانی استاد ----------------
    @Override
    public TeacherUpdateDto update(Long id, TeacherUpdateDto dto) {
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

        Teacher updated = teacherMapper.toUpdateEntity(dto);
        // نگه داشتن فیلدهای اصلی
        updated.setId(existing.getId());
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setUser(existing.getUser());
        updated.setDepartment(existing.getDepartment());

        Teacher saved = teacherRepository.save(updated);
        return teacherMapper.toUpdateDto(saved);
    }

    // ---------------- یافتن استاد بر اساس id ----------------
    @Override
    @Transactional(readOnly = true)
    public TeacherUpdateDto findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        return teacherMapper.toUpdateDto(teacher);
    }

    // ---------------- لیست همه اساتید بدون صفحه‌بندی ----------------
    @Override
    @Transactional(readOnly = true)
    public List<TeacherUpdateDto> findAll() {
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::toUpdateDto)
                .toList();
    }

    // ---------------- لیست اساتید با صفحه‌بندی ----------------
    @Transactional(readOnly = true)
    public Page<TeacherUpdateDto> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable)
                .map(teacherMapper::toUpdateDto);
    }

    // ---------------- حذف استاد ----------------
    @Override
    public void deleteById(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }

    // ---------------- بررسی وجود استاد ----------------
    @Override
    public boolean existsById(Long id) {
        return teacherRepository.existsById(id);
    }

    // ---------------- گرفتن reference بدون لود کامل ----------------
    @Override
    public Teacher getReferenceById(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        return teacherRepository.getReferenceById(id);
    }
}