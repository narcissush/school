package com.mftplus.school.lesson.dto;

import com.mftplus.school.core.model.Teacher;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LessonDto {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid Name")
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    private Teacher teacher;

    @Min(value = 1, message = "unit must be at least 1")
    @Max(value = 5, message = "unit must be at least 5")
    private int unit;


    @Future(message = "class time must be in future")
    private LocalDateTime classTime;


    @Min(value = 1, message = "class number must be positive")
    private int classNumber;

    private boolean deleted = false;
}
