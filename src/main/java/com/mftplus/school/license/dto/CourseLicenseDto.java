package com.mftplus.school.license.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseLicenseDto {

    private Long id;

    @NotNull(message = "Student is required")
    private Long studentId;

    @NotNull(message = "License Number is required")
    private String licenseNumber;

    @NotNull(message = "Certificate Date is required")
    private LocalDate certificateDate;

    @NotNull(message = "Expiry Date is required")
    private LocalDate expiryDate;

    @NotNull(message = "Institute Issued is required")
    private String instituteIssued;
}