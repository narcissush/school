package com.mftplus.school.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name="teacherEntity")
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends Person {

    @NotBlank(message = "شماره پرسنلی الزامی است")
    @Size(max = 20, message = "شماره پرسنلی نمی‌تواند بیشتر از 20 کاراکتر باشد")
    @Column(unique = true, length = 20)
    private String teacherNumber;

//    @ManyToMany
//    @JoinTable(
//            name = "teacher_skill",
//            joinColumns = @JoinColumn(name = "teacher_id"),
//            inverseJoinColumns = @JoinColumn(name = "skill_id")
//    )
//    private List<Skill> skillList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "teacher")
//    private List<Lesson> lessonList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "teacher")
//    private List<Course> courseList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "teacher")
//    private List<Schedule> courseScheduleList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Experience> experienceList = new ArrayList<>();
}
