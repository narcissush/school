package com.mftplus.school.core.model;

import com.mftplus.school.course.model.Course;
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

    @OneToMany(mappedBy = "student")
    private List<Course> courseList = new ArrayList<>();

//    @OneToMany(mappedBy = "student")
//    private List<License> licenseList = new ArrayList<>();
}
