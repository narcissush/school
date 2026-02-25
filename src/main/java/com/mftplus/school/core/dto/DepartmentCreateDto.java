package com.mftplus.school.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCreateDto {
    @NotBlank(message = "نام دپارتمان الزامی است")
    @Size(min = 2, max = 100, message = "نام دپارتمان باید بین 2 تا 100 کاراکتر باشد")
    private String name;

    @Size(max = 500, message = "توضیحات نمی‌تواند بیشتر از 500 کاراکتر باشد")
    private String description;

    private Boolean active = true;
}
