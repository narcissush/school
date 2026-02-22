package com.mftplus.school.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="teacherEntity")
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Teacher extends Person {
    private String teacherNumber;

//    @ManyToMany
//    @JoinTable(
//            name = "teacher_skill",
//            joinColumns = @JoinColumn(name = "teacher_id"),
//            inverseJoinColumns = @JoinColumn(name = "skill_id")
//    )
//    private List<Teacher> teacherList;
//
//    @OneToMany(mappedBy = "teacher")
//    private List<Lesson> lessonList;
//
//    @OneToMany(mappedBy = "teacher")
//    private List<Course> courseList;
//
//    @OneToMany(mappedBy = "teacher")
//    private List<CourseSchedule> courseScheduleList;

}
