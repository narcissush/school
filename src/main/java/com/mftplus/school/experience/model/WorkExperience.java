package com.mftplus.school.experience.model;

import com.mftplus.school.core.model.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity(name = "workExperienceEntity")
@Table(name = "work_experiences")

@SQLDelete(sql = "UPDATE work_experiences SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class WorkExperience extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "teacherId", nullable = false)
    private Teacher teacher;

    @Column(name = "company_name", nullable = false, length = 100)
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{3,100}$", message = "Invalid CompanyName")
    private String companyName;

    @Column(name = "job_title", nullable = false, length = 20)
    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid JobTitle")
    private String jobTitle;

    @Column(name = "start_year", nullable = false)
    private LocalDate startYear;

    @Column(name = "end_year", nullable = false)
    private LocalDate endYear;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

}
