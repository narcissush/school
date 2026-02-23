package com.mftplus.school.controller;

import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.mapper.TeacherMapper;
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
    private final TeacherMapper teacherMapper;

    // ---------------- لیست اساتید ----------------
    @GetMapping
    public String listTeachers(Model model) {
        List<TeacherUpdateDto> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("title", "لیست اساتید");
        return "teacher/new-teacher";
    }

    // ---------------- فرم ایجاد استاد ----------------
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("teacher", new TeacherCreateDto());
        model.addAttribute("departments", departmentService.findAllActive());
        model.addAttribute("title", "افزودن استاد جدید");
        return "teacher/create";
    }

    // ---------------- ذخیره استاد جدید ----------------
    @PostMapping("/create")
    public String createTeacher(@Valid @ModelAttribute("teacher") TeacherCreateDto teacherDto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAllActive());
            return "teacher/create";
        }

        try {
            teacherService.create(teacherDto);
            redirectAttributes.addFlashAttribute("success", "استاد با موفقیت ایجاد شد");
            return "redirect:/teachers";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("departments", departmentService.findAllActive());
            return "teacher/create";
        }
    }

    // ---------------- فرم ویرایش استاد ----------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            TeacherUpdateDto teacher = teacherService.findById(id);
            model.addAttribute("teacher", teacher);
            model.addAttribute("departments", departmentService.findAllActive());
            model.addAttribute("title", "ویرایش استاد");
            return "teacher/edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "استاد یافت نشد");
            return "redirect:/teachers";
        }
    }

    // ---------------- بروزرسانی استاد ----------------
    @PostMapping("/edit/{id}")
    public String updateTeacher(@PathVariable Long id,
                                @Valid @ModelAttribute("teacher") TeacherUpdateDto teacherDto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAllActive());
            return "teacher/edit";
        }

        try {
            teacherService.update(id, teacherDto);
            redirectAttributes.addFlashAttribute("success", "استاد با موفقیت به‌روزرسانی شد");
            return "redirect:/teachers";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("departments", departmentService.findAllActive());
            return "teacher/edit";
        }
    }

    // ---------------- حذف استاد ----------------
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

    // ---------------- مشاهده جزئیات استاد ----------------
    @GetMapping("/view/{id}")
    public String viewTeacher(@PathVariable Long id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            TeacherUpdateDto teacher = teacherService.findById(id);
            model.addAttribute("teacher", teacher);
            model.addAttribute("title", "مشاهده استاد");
            return "teacher/view";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "استاد یافت نشد");
            return "redirect:/teachers";
        }
    }
}