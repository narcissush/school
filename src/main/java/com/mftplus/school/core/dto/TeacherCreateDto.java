package com.mftplus.school.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCreateDto {

    @NotBlank(message = "نام الزامی است")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "نام خانوادگی الزامی است")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank(message = "کد ملی الزامی است")
    @Pattern(regexp = "^[0-9]{10}$", message = "کد ملی باید ۱۰ رقم باشد")
    private String nationalCode;

    @Email(message = "ایمیل معتبر نیست")
    @Size(max = 100)
    private String email;

    @Pattern(regexp = "^09[0-9]{9}$", message = "شماره موبایل معتبر نیست")
    private String mobile;

    private Long departmentId;  // فقط آی‌دی دپارتمان

    private Boolean active = true;  // مقدار پیشفرض

    @NotBlank(message = "شماره پرسنلی الزامی است")
    @Size(max = 20)
    private String teacherNumber;

    // اصلاح شد: لیست مهارت‌ها به جای teacherIds
    private List<Long> skillIds = new ArrayList<>();
    private List<Long> lessonIds = new ArrayList<>();
    private List<Long> courseIds = new ArrayList<>();
    private List<Long> courseScheduleIds = new ArrayList<>();
    private List<Long> experienceIds = new ArrayList<>();
}

