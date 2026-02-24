package com.mftplus.school.experience.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter

public class WorkExperienceDto {

    private Long id;

    private Long teacherId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]{3,100}$", message = "Invalid CompanyName")
    private String companyName;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid JobTitle")
    private String jobTitle;

    private LocalDate startYear;

    private LocalDate endYear;

    private boolean deleted;

    private String description;

}
