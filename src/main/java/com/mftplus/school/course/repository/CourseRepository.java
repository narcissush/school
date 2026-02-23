package com.mftplus.school.course.repository;

import com.mftplus.school.course.model.Course;
import com.mftplus.school.course.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
