package com.mftplus.school.controller;

import com.mftplus.school.core.dto.TeacherCreateDto;
import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.service.DepartmentService;
import com.mftplus.school.core.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final DepartmentService departmentService;
    // private final SkillService skillService; // برای بعد
    // private final ExperienceService experienceService; // برای بعد

    // ---------------- لیست اساتید ----------------
    @GetMapping
    public String listTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TeacherUpdateDto> teacherPage = teacherService.findAll(pageable);

        model.addAttribute("teachers", teacherPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", teacherPage.getTotalPages());
        model.addAttribute("totalItems", teacherPage.getTotalElements());

        model.addAttribute("title", "لیست اساتید");
        model.addAttribute("content", "teacher/list");
        return "layout";
    }

    // ---------------- فرم ایجاد استاد ----------------
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("teacher", new TeacherCreateDto());
        model.addAttribute("departments", departmentService.findAllActive());
        // model.addAttribute("skills", skillService.findAll());
        // model.addAttribute("experiences", experienceService.findAll());
        model.addAttribute("title", "افزودن استاد جدید");
        model.addAttribute("content", "teacher/create");
        return "layout";
    }

    // ---------------- ذخیره استاد جدید ----------------
    @PostMapping("/create")
    public String createTeacher(@Valid @ModelAttribute("teacher") TeacherCreateDto teacherDto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAllActive());
            // model.addAttribute("skills", skillService.findAll());
            // model.addAttribute("experiences", experienceService.findAll());
            model.addAttribute("title", "افزودن استاد جدید");
            model.addAttribute("content", "teacher/create");
            return "layout";
        }

        try {
            teacherService.create(teacherDto);
            redirectAttributes.addFlashAttribute("success", "استاد با موفقیت ایجاد شد");
            return "redirect:/teachers";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("departments", departmentService.findAllActive());
            // model.addAttribute("skills", skillService.findAll());
            // model.addAttribute("experiences", experienceService.findAll());
            model.addAttribute("title", "افزودن استاد جدید");
            model.addAttribute("content", "teacher/create");
            return "layout";
        }
    }

    // ---------------- فرم ویرایش استاد ----------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            TeacherUpdateDto teacher = teacherService.findById(id);
            model.addAttribute("teacher", teacher);
            model.addAttribute("departments", departmentService.findAllActive());
            // model.addAttribute("skills", skillService.findAll());
            // model.addAttribute("experiences", experienceService.findAll());
            model.addAttribute("title", "ویرایش استاد");
            model.addAttribute("content", "teacher/edit");
            return "layout";
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

        teacherDto.setId(id);

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAllActive());
            // model.addAttribute("skills", skillService.findAll());
            // model.addAttribute("experiences", experienceService.findAll());
            model.addAttribute("title", "ویرایش استاد");
            model.addAttribute("content", "teacher/edit");
            return "layout";
        }

        try {
            teacherService.update(id, teacherDto);
            redirectAttributes.addFlashAttribute("success", "استاد با موفقیت به‌روزرسانی شد");
            return "redirect:/teachers";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("departments", departmentService.findAllActive());
            // model.addAttribute("skills", skillService.findAll());
            // model.addAttribute("experiences", experienceService.findAll());
            model.addAttribute("title", "ویرایش استاد");
            model.addAttribute("content", "teacher/edit");
            return "layout";
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
            model.addAttribute("content", "teacher/view");
            return "layout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "استاد یافت نشد");
            return "redirect:/teachers";
        }
    }
}