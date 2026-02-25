package com.mftplus.school.core.model;

import com.mftplus.school.course.model.Course;
import com.mftplus.school.course.model.Schedule;
import com.mftplus.school.lesson.model.Lesson;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity(name="teacherEntity")
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends Person {

    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String teacherNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToMany
//    @JoinTable(
//            name = "teacher_skill",
//            joinColumns = @JoinColumn(name = "teacher_id"),
//            inverseJoinColumns = @JoinColumn(name = "skill_id")
//    )
//    private List<Skill> skillList = new ArrayList<>();

   @OneToMany(mappedBy = "teacher")
   private List<Lesson> lessonList = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<Course> courseList = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> courseScheduleList = new ArrayList<>();

//    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Experience> experienceList = new ArrayList<>();
}
