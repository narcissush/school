package com.mftplus.school.license.model;

import com.mftplus.school.core.model.Student;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity(name = "courseLicenseEntity")
@Table(name = "course_licenses")

@SQLDelete(sql = "UPDATE course_licenses SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseLicense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "license_number", length = 30, nullable = false, unique = true)
    private String licenseNumber;

    @Column(name = "certificate_date", nullable = false)
    private LocalDate certificateDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "institute_issued", length = 80, nullable = false)
    private String instituteIssued;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
}