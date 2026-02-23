package com.mftplus.school.course.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data

public class CourseUpdateDto {


    private Long id;

    @NotNull(message = "معلم الزامی است")
    private Long teacherId;

    private List<Long> studentIds = new ArrayList<>();

    @NotNull(message = "درس الزامی است")
    private Long lessonId;

    private Long courseScheduleId;

}
