package com.mftplus.school.course.model;

import com.mftplus.school.core.model.Student;
import com.mftplus.school.core.model.Teacher;
import com.mftplus.school.lesson.model.Lesson;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;


@SQLDelete(sql = "UPDATE courseEntity SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")

@Entity(name = "courseEntity")
@Table(name = "courses")

@NoArgsConstructor
@Data

public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_schedule_id")
    private Schedule schedule;

}
