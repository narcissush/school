package com.mftplus.school.course.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

public class CourseCreateDto {


    @NotNull(message = "معلم الزامی است")
    private Long teacherId;

    private String title;
    private String classNumber;

    private List<Long> studentIds = new ArrayList<>();

//    @NotNull(message = "درس الزامی است")
//    private Long lessonId;

    private Long ScheduleId;

}
