package com.mftplus.school.controller;

import com.mftplus.school.core.dto.StudentCreateDto;
import com.mftplus.school.core.dto.StudentUpdateDto;
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

    @GetMapping
    public String listStudents(Model model) {
        List<StudentCreateDto> students = studentService.findAll();
        model.addAttribute("students", students);
        model.addAttribute("title", "لیست دانشجویان");
        return "student/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new StudentCreateDto());
        List<Department> departments = departmentService.findAllActive();
        model.addAttribute("departments", departments);
        model.addAttribute("title", "افزودن دانشجو جدید");
        return "student/create";
    }

    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute("student") StudentCreateDto studentDto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            List<Department> departments = departmentService.findAllActive();
            model.addAttribute("departments", departments);
            return "student/create";
        }

        try {
            studentService.create(studentDto);
            redirectAttributes.addFlashAttribute("success", "دانشجو با موفقیت ایجاد شد");
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Department> departments = departmentService.findAllActive();
            model.addAttribute("departments", departments);
            return "student/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            StudentCreateDto student = studentService.findById(id);
            model.addAttribute("student", student);
            List<Department> departments = departmentService.findAllActive();
            model.addAttribute("departments", departments);
            model.addAttribute("title", "ویرایش دانشجو");
            return "student/edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "دانشجو یافت نشد");
            return "redirect:/students";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") StudentUpdateDto studentDto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            List<Department> departments = departmentService.findAllActive();
            model.addAttribute("departments", departments);
            return "student/edit";
        }

        try {
            studentService.update(id, studentDto);
            redirectAttributes.addFlashAttribute("success", "دانشجو با موفقیت به‌روزرسانی شد");
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            List<Department> departments = departmentService.findAllActive();
            model.addAttribute("departments", departments);
            return "student/edit";
        }
    }

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

    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable Long id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            StudentCreateDto student = studentService.findById(id);
            model.addAttribute("student", student);
            model.addAttribute("title", "مشاهده دانشجو");
            return "student/view";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "دانشجو یافت نشد");
            return "redirect:/students";
        }
    }
}
