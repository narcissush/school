package com.mftplus.school.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="studentEntity")
//@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Student extends Person {
    private String studentNumber;


//    @OneToMany(mappedBy = "student")
//    private List<Course> courseList;
//
//    @OneToMany(mappedBy = "student")
//    private List<License> licenseList;

}
