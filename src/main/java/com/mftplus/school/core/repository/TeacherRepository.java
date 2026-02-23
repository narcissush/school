package com.mftplus.school.core.repository;

import com.mftplus.school.core.model.Student;
import com.mftplus.school.core.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
