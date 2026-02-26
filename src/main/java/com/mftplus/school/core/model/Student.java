package com.mftplus.school.core.model;

import com.mftplus.school.course.model.Course;
import com.mftplus.school.license.model.CourseLicense;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name="studentEntity")
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Person {


    @NotBlank(message = "شماره دانشجویی الزامی است")
    @Size(max = 20, message = "شماره دانشجویی نمی‌تواند بیشتر از 20 کاراکتر باشد")
    @Column(unique = true, length = 20)
    private String studentNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "students")
    private List<Course> courseList = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<CourseLicense> licenseList = new ArrayList<>();
}
