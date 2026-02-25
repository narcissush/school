package com.mftplus.school.core.service;


import com.mftplus.school.core.dto.DepartmentCreateDto;
import com.mftplus.school.core.dto.DepartmentUpdateDto;
import com.mftplus.school.core.mapper.DepartmentMapper;
import com.mftplus.school.core.model.Department;
import com.mftplus.school.core.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;



    @Cacheable(value = "departments", key = "'all'")
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Cacheable(value = "departments", key = "'active'")
    @Transactional(readOnly = true)
    public List<Department> findAllActive() {
        return departmentRepository.findByActiveTrue();
    }

    @Cacheable(value = "departments", key = "#id")
    @Transactional(readOnly = true)
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    // 🔥 ذخیره با DTO + Mapper
    @CacheEvict(value = "departments", allEntries = true)
    @Transactional
    public DepartmentCreateDto save(DepartmentCreateDto dto) {

        if (departmentRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("دپارتمان با این نام قبلاً ثبت شده است");
        }

        Department department = departmentMapper.toCreateEntity(dto);
        Department saved = departmentRepository.save(department);

        return departmentMapper.toCreateDto(saved);
    }

    // 🔥 آپدیت حرفه‌ای
    @CacheEvict(value = "departments", allEntries = true)
    @Transactional
    public DepartmentUpdateDto update(DepartmentUpdateDto dto) {

        Department existing = departmentRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("دپارتمان یافت نشد"));

        // مپ کردن فیلدهای جدید روی آبجکت موجود (بهتر برای JPA)
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setActive(dto.getActive());

        Department updated = departmentRepository.save(existing);

        return departmentMapper.toUpdateDto(updated);
    }

    @CacheEvict(value = "departments", allEntries = true)
    @Transactional
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }
}