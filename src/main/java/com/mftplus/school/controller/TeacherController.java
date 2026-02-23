package com.mftplus.school.controller;

import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.model.Department;
import com.mftplus.school.core.service.DepartmentService;
import com.mftplus.school.core.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final DepartmentService departmentService;

    @GetMapping
    public String listTeachers(Model model) {
        List<TeacherCreateDto> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("title", "لیست اساتید");
        return "teacher/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("teacher", new TeacherCreateDto());
        List<Department> departments = departmentService.findAllActive();
        model.addAttribute("departments", departments);
        model.addAttribute("title", "افزودن استاد جدید");
        return "teacher/create";
    }

    @PostMapping("/create")
    public String createTeacher(@Valid @ModelAttribute("teacher") TeacherCreateDto teacherDto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            List<Department> departments = departmentService.findAllActive();
            model.addAttribute("departments", departments);
            return "teacher/create";
        }

        try {
            teacherService.create(teacherDto);
            redirectAttributes.addFlashAttribute("success", "استاد با موفقیت ایجاد شد");
            return "redirect:/teachers";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Department> departments = departmentService.findAllActive();
            model.addAttribute("departments", departments);
            return "teacher/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            TeacherCreateDto teacher = teacherService.findById(id);
            model.addAttribute("teacher", teacher);
            List<Department> departments = departmentService.findAllActive();
            model.addAttribute("departments", departments);
            model.addAttribute("title", "ویرایش استاد");
            return "teacher/edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "استاد یافت نشد");
            return "redirect:/teachers";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateTeacher(@PathVariable Long id,
                                @Valid @ModelAttribute("teacher") TeacherUpdateDto teacherDto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            List<Department> departments = departmentService.findAllActive();
            model.addAttribute("departments", departments);
            return "teacher/edit";
        }

        try {
            teacherService.update(id, teacherDto);
            redirectAttributes.addFlashAttribute("success", "استاد با موفقیت به‌روزرسانی شد");
            return "redirect:/teachers";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Department> departments = departmentService.findAllActive();
            model.addAttribute("departments", departments);
            return "teacher/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            teacherService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "استاد با موفقیت حذف شد");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/teachers";
    }

    @GetMapping("/view/{id}")
    public String viewTeacher(@PathVariable Long id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            TeacherCreateDto teacher = teacherService.findById(id);
            model.addAttribute("teacher", teacher);
            model.addAttribute("title", "مشاهده استاد");
            return "teacher/view";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "استاد یافت نشد");
            return "redirect:/teachers";
        }
    }
}
