package com.mftplus.school.course.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ScheduleCreateDto {

    @NotNull(message = "استاد الزامی است")
    private Long teacherId; // استاد مربوطه

    @NotNull(message = "شروع دوره الزامی است")
    private LocalDate startDate;

    @NotNull(message = "پایان دوره الزامی است")
    private LocalDate endDate;

    @NotNull(message = "ساعت شروع کلاس الزامی است")
    private LocalTime startTime;

    @NotNull(message = "ساعت پایان کلاس الزامی است")
    private LocalTime endTime;

    private List<DayOfWeek> daysOfWeek = new ArrayList<>();

    private Long courseId;
}
