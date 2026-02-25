package com.mftplus.school.lesson.model;

import com.mftplus.school.core.model.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "lessonEntity")
@Table(name = "lessons")

@SQLDelete(sql = "update lessons set deleted =true where id=?")
@SQLRestriction("deleted=false")


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid Name")
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid Teacher")
    @Column(name = "teacher", nullable = false, length = 20)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Min(value = 1, message = "unit must be at least 1")
    @Max(value = 5, message = "unit must be at least 5")
    @Column(name = "unit", nullable = false)
    private int unit;

    @Column(name = "class_time", nullable = false)
    @Future(message = "class time must be in future")
    private LocalDateTime classTime;

    @Column(name = "class_number", nullable = false)
    @Min(value = 1, message = "class number must be positive")
    private int classNumber;

    private boolean deleted = false;
}
