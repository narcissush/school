package com.mftplus.school.core.service;

import com.mftplus.school.core.dto.StudentCreateDto;
import com.mftplus.school.core.dto.StudentUpdateDto;
import com.mftplus.school.core.mapper.StudentMapper;
import com.mftplus.school.core.model.Department;
import com.mftplus.school.core.model.Student;
import com.mftplus.school.core.repository.DepartmentRepository;
import com.mftplus.school.core.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImp implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final DepartmentRepository departmentRepository;

    @Override
    public StudentCreateDto create(StudentCreateDto dto) {

        Student student = studentMapper.toCreateEntity(dto);

        // 🔥 خط حیاتی پروژه تو
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("دپارتمان یافت نشد"));

        student.setDepartment(department);

        Student savedStudent = studentRepository.save(student);
        return studentMapper.toCreateDto(savedStudent);
    }

    @Override
    public StudentUpdateDto update(Long id, StudentUpdateDto dto) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        Student updatedStudent = studentMapper.toUpdateEntity(dto);

        updatedStudent.setId(existingStudent.getId());
        updatedStudent.setCreatedAt(existingStudent.getCreatedAt());
        updatedStudent.setDepartment(existingStudent.getDepartment());
        updatedStudent.setUser(existingStudent.getUser());

        Student saved = studentRepository.save(updatedStudent);
        return studentMapper.toUpdateDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentUpdateDto findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return studentMapper.toUpdateDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentUpdateDto> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(studentMapper::toUpdateDto);
    }

    @Override
    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getReferenceById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        return studentRepository.getReferenceById(id);
    }
}
