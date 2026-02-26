package com.mftplus.school.course.repository;

import com.mftplus.school.course.model.Schedule;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTeacherId(Long teacherId);

    List<Schedule> findByTeacherIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long teacherId, java.time.LocalDate endDate, java.time.LocalDate startDate
    );

}
