package com.mftplus.school.controller;

import com.mftplus.school.core.dto.StudentCreateDto;
import com.mftplus.school.core.dto.StudentUpdateDto;
import com.mftplus.school.core.mapper.StudentMapper;
import com.mftplus.school.core.model.Department;
import com.mftplus.school.core.service.DepartmentService;
import com.mftplus.school.core.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final StudentMapper studentMapper;

    // ---------------- لیست دانشجویان ----------------
    @GetMapping
    public String listStudents(Model model) {
        List<StudentUpdateDto> students = studentService.findAll();
        model.addAttribute("students", students);
        model.addAttribute("title", "لیست دانشجویان");
        return "student/list";
    }

    // ---------------- فرم ایجاد دانشجو ----------------
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new StudentCreateDto());
        model.addAttribute("departments", departmentService.findAllActive());
        model.addAttribute("title", "افزودن دانشجو جدید");
        return "student/create";
    }

    // ---------------- ذخیره دانشجوی جدید ----------------
    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute("student") StudentCreateDto studentDto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAllActive());
            return "student/create";
        }

        try {
            studentService.create(studentDto);
            redirectAttributes.addFlashAttribute("success", "دانشجو با موفقیت ایجاد شد");
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("departments", departmentService.findAllActive());
            return "student/create";
        }
    }

    // ---------------- فرم ویرایش دانشجو ----------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            StudentUpdateDto student = studentService.findById(id);
            model.addAttribute("student", student);
            model.addAttribute("departments", departmentService.findAllActive());
            model.addAttribute("title", "ویرایش دانشجو");
            return "student/edit";
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
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAllActive());
            return "student/edit";
        }

        try {
            studentService.update(id, studentDto);
            redirectAttributes.addFlashAttribute("success", "دانشجو با موفقیت به‌روزرسانی شد");
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("departments", departmentService.findAllActive());
            return "student/edit";
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
            return "student/view";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "دانشجو یافت نشد");
            return "redirect:/students";
        }
    }
}