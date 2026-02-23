package com.mftplus.school.course.model;

import com.mftplus.school.core.model.Teacher;
import com.mftplus.school.course.model.Course;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "schedules")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // بازه تاریخ
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    // بازه ساعت
    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    // روزهای هفته
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "schedule_days", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "day_of_week")
    private List<DayOfWeek> daysOfWeek = new ArrayList<>();

    // استاد مربوطه
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // کلاس اختصاص داده شده (اختیاری)
    @OneToOne(mappedBy = "schedule")
    private Course course;

    // متد اعتبارسنجی زمان‌ها
    public void validateTimes() {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("تاریخ پایان نمی‌تواند قبل از تاریخ شروع باشد");
        }
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("ساعت پایان باید بعد از ساعت شروع باشد");
        }
        if (daysOfWeek == null || daysOfWeek.isEmpty()) {
            throw new IllegalArgumentException("روزهای هفته باید مشخص شوند");
        }
    }
}