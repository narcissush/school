package com.mftplus.school.course.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@SQLDelete(sql = "UPDATE scheduleEntity SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")

@Entity(name="scheduleEntity")
@Table(name = "schedules")

@NoArgsConstructor
@Data

public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sschedule_days", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "day_of_week")
    private List<DayOfWeek> daysOfWeek = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // می‌تونی متد validation زمان‌بندی هم اضافه کنی
    public void validateTimes() {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("تاریخ پایان نمی‌تواند قبل از تاریخ شروع باشد");
        }
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("ساعت پایان باید بعد از ساعت شروع باشد");
        }
    }
}

