package com.mftplus.school.controller;

import com.mftplus.school.core.dto.StudentCreateDto;
import com.mftplus.school.core.dto.StudentUpdateDto;
import com.mftplus.school.core.service.DepartmentService;
import com.mftplus.school.core.service.StudentService;
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

import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final DepartmentService departmentService;

    // ---------------- لیست دانشجویان ----------------
    @GetMapping
    public String listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<StudentUpdateDto> studentPage = studentService.findAll(pageable);

        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalItems", studentPage.getTotalElements());

        model.addAttribute("title", "لیست دانشجویان");
        model.addAttribute("content", "student/list");
        return "layout";
    }

    // ---------------- فرم ایجاد دانشجو ----------------
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new StudentCreateDto());
        model.addAttribute("departments", departmentService.findAllActive());
        model.addAttribute("title", "افزودن دانشجو جدید");
        model.addAttribute("content", "student/create");
        return "layout";
    }

    // ---------------- ذخیره دانشجوی جدید ----------------
    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute("student") StudentCreateDto studentDto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAllActive());
            model.addAttribute("title", "افزودن دانشجو جدید");
            model.addAttribute("content", "student/create");
            return "layout"; // 🔥 مهم: نه student/create
        }

        try {
            studentService.create(studentDto);
            redirectAttributes.addFlashAttribute("success", "دانشجو با موفقیت ایجاد شد");
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("departments", departmentService.findAllActive());
            model.addAttribute("title", "افزودن دانشجو جدید");
            model.addAttribute("content", "student/create");
            return "layout";
        }
    }

    // ---------------- فرم ویرایش دانشجو ----------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            StudentUpdateDto student = studentService.findById(id);
            model.addAttribute("student", student);
            model.addAttribute("departments", departmentService.findAllActive());
            model.addAttribute("title", "ویرایش دانشجو");
            model.addAttribute("content", "student/edit");
            return "layout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "دانشجو یافت نشد");
            return "redirect:/students";
        }
    }

    // ---------------- بروزرسانی دانشجو ----------------
    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") StudentUpdateDto studentDto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        // خیلی مهم: ست کردن id داخل dto
        studentDto.setId(id);

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAllActive());
            model.addAttribute("title", "ویرایش دانشجو");
            model.addAttribute("content", "student/edit");
            return "layout";
        }

        try {
            studentService.update(id, studentDto);
            redirectAttributes.addFlashAttribute("success", "دانشجو با موفقیت به‌روزرسانی شد");
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("departments", departmentService.findAllActive());
            model.addAttribute("title", "ویرایش دانشجو");
            model.addAttribute("content", "student/edit");
            return "layout";
        }
    }

    // ---------------- حذف دانشجو ----------------
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "دانشجو با موفقیت حذف شد");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/students";
    }

    // ---------------- مشاهده جزئیات دانشجو ----------------
    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable Long id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            StudentUpdateDto student = studentService.findById(id);
            model.addAttribute("student", student);
            model.addAttribute("title", "مشاهده دانشجو");
            model.addAttribute("content", "student/view");
            return "layout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "دانشجو یافت نشد");
            return "redirect:/students";
        }
    }
}