package com.mftplus.school.course.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data

public class ScheduleCreateDto {


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
